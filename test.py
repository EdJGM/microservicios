from locust import HttpUser, task, between, TaskSet
import random
import time
import json
import uuid

class PublicacionesUser(HttpUser):
    wait_time = between(1, 3)
    host = "http://localhost:8080"
    
    # Lista para almacenar IDs de autores creados
    autor_ids = []
    
    # Conjuntos para mantener emails y orcids usados y evitar duplicados
    used_emails = set()
    used_orcids = set()
    used_isbns = set()
    used_dois = set()
    
    def on_start(self):
        """Método que se ejecuta cuando un usuario comienza sus tareas"""
        # Intentar obtener la lista de autores al inicio para usarlos en las publicaciones
        try:
            response = self.client.get("/publicaciones/autor")
            if response.status_code == 200:
                response_data = json.loads(response.text)
                # Extraer IDs de autores si hay datos disponibles
                for item in response_data:
                    if isinstance(item, dict) and 'data' in item and item['data'] and 'id' in item['data']:
                        self.autor_ids.append(item['data']['id'])
        except Exception as e:
            print(f"Error al cargar autores existentes: {e}")
    
    @task(1)
    def crear_autor(self):
        """Tarea para crear un nuevo autor con datos únicos"""
        # Generar datos únicos para evitar conflictos
        nombre = f"A{random.randint(1, 999)}"
        apellido = f"B{random.randint(1, 999)}"
        
        # Generar email único (CORREGIDO: max 20 caracteres)
        email = None
        while email is None or email in self.used_emails:
            # Usamos letras cortas para mantener el email dentro del límite de 20 caracteres
            email = f"a{random.randint(1, 999)}@c.com"
        self.used_emails.add(email)
        
        # Generar orcid único
        orcid = None
        while orcid is None or orcid in self.used_orcids:
            orcid = f"0000-0000-0000-{uuid.uuid4().hex[:4]}"
        self.used_orcids.add(orcid)
        
        # Datos del autor
        payload = {
            "nombre": nombre,
            "apellido": apellido,
            "email": email,
            "orcid": orcid,
            "institucion": f"Uni{random.randint(1, 99)}",
            "nacionalidad": random.choice(["Perú", "Ecuador", "Colombia", "México", "Chile"]),
            "telefono": f"+{random.randint(10, 99)}{random.randint(1000000, 9999999)}",
            "fechaNacimiento": f"{random.randint(1950, 2000)}-{random.randint(1, 12):02d}-{random.randint(1, 28):02d}"
        }
        
        # Realizar la solicitud POST
        with self.client.post("/publicaciones/autor", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                try:
                    data = json.loads(response.text)
                    if data.get('data') and 'id' in data.get('data'):
                        autor_id = data['data']['id']
                        self.autor_ids.append(autor_id)
                        print(f"✅ Autor creado correctamente con ID: {autor_id}")
                        response.success()
                    else:
                        print(f"❌ Error en el formato de respuesta: {response.text}")
                        response.failure("Formato de respuesta inválido")
                except json.JSONDecodeError:
                    print(f"❌ Error al parsear respuesta: {response.text}")
                    response.failure("Error al parsear JSON")
            else:
                print(f"❌ Error al crear autor: {response.status_code} - {response.text}")
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(2)
    def crear_libro(self):
        """Tarea para crear un nuevo libro asociado a un autor existente"""
        if not self.autor_ids:
            print("⚠️ No hay autores disponibles para asociar a un libro")
            return
        
        # Seleccionar un autor aleatorio
        id_autor = random.choice(self.autor_ids)
        
        # Generar ISBN único
        isbn = None
        while isbn is None or isbn in self.used_isbns:
            isbn = f"978-{random.randint(10, 99)}-{random.randint(1000, 9999)}-{random.randint(10, 99)}-{random.randint(1, 9)}"
        self.used_isbns.add(isbn)
        
        # Datos del libro
        payload = {
            "titulo": f"Libro {uuid.uuid4().hex[:8]}",
            "anioPublicacion": random.randint(2000, 2025),
            "editorial": random.choice(["Planeta", "Anagrama", "Penguin", "Ed B"]),
            "isbn": isbn,
            "resumen": f"Libro sobre tema interesante",
            "idioma": random.choice(["Español", "Inglés", "Francés", "Alemán"]),
            "genero": random.choice(["Ficción", "No ficción", "Ciencia", "Biografía"]),
            "numeroPaginas": random.randint(100, 800),
            "edicion": f"{random.randint(1, 10)}ª",
            "idAutor": id_autor
        }
        
        # Realizar la solicitud POST
        with self.client.post("/publicaciones/libro", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                print(f"✅ Libro creado correctamente para el autor {id_autor}")
                response.success()
            else:
                print(f"❌ Error al crear libro: {response.status_code} - {response.text}")
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(2)
    def crear_articulo(self):
        """Tarea para crear un nuevo artículo asociado a un autor existente"""
        if not self.autor_ids:
            print("⚠️ No hay autores disponibles para asociar a un artículo")
            return
        
        # Seleccionar un autor aleatorio
        id_autor = random.choice(self.autor_ids)
        
        # Generar ISBN único
        isbn = None
        while isbn is None or isbn in self.used_isbns:
            isbn = f"978-{random.randint(10, 99)}-{random.randint(1000, 9999)}-{random.randint(10, 99)}-{random.randint(1, 9)}"
        self.used_isbns.add(isbn)
        
        # Generar DOI único
        doi = None
        while doi is None or doi in self.used_dois:
            doi = f"10.{random.randint(1000, 9999)}/{uuid.uuid4().hex[:10]}"
        self.used_dois.add(doi)
        
        # Datos del artículo
        payload = {
            "titulo": f"Artículo {uuid.uuid4().hex[:8]}",
            "anioPublicacion": random.randint(2000, 2025),
            "editorial": random.choice(["IEEE", "ACM", "Springer", "Elsevier"]),
            "isbn": isbn,
            "resumen": f"Artículo científico relevante",
            "idioma": random.choice(["Español", "Inglés", "Francés"]),
            "revista": random.choice(["Journal CS", "IEEE Trans", "Nature", "Science"]),
            "doi": doi,
            "areaInvestigacion": random.choice(["Computación", "Biología", "Física", "Química"]),
            "fechaPublicacion": f"{random.randint(2000, 2025)}-{random.randint(1, 12):02d}-{random.randint(1, 28):02d}",
            "idAutor": id_autor
        }
        
        # Realizar la solicitud POST
        with self.client.post("/publicaciones/articulo", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                print(f"✅ Artículo creado correctamente para el autor {id_autor}")
                response.success()
            else:
                print(f"❌ Error al crear artículo: {response.status_code} - {response.text}")
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(1)
    def listar_autores(self):
        """Tarea para listar autores"""
        with self.client.get("/publicaciones/autor", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(1)
    def listar_libros(self):
        """Tarea para listar libros"""
        with self.client.get("/publicaciones/libro", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(1)
    def listar_articulos(self):
        """Tarea para listar artículos"""
        with self.client.get("/publicaciones/articulo", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(1)
    def listar_catalogo(self):
        """Tarea para listar el catálogo completo"""
        with self.client.get("http://localhost:8082/catalogo", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Error HTTP {response.status_code}")
    
    @task(1)
    def listar_notificaciones(self):
        """Tarea para listar notificaciones"""
        with self.client.get("http://localhost:8081/notificaciones", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Error HTTP {response.status_code}")