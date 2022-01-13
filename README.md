# recetasApi

DESCRIPCIÓN

Creación de API REST con recetas de cocina, implementando todas las operaciones propias de un CRUD.

API

GET /recetas -> Obtención de todas las recetas

DEL /receta/{id} -> Eliminar una receta por ID

GET /search/recetaById/{id} -> Obtención de una receta por ID

GET /search/ingredients/{ingrediente} -> Obtención de todas las recetas que contienen un ingrediente

GET /search/categories/{categoria} -> Obtención de todas las recetas de una categoría

PATCH /receta/{id} -> Actualización de los datos de una receta a través de su id

POST /recetas -> Creación de una receta

    Body de ejemplo:
    {"name": "Pollo al chilindrón", 
    "pasos": {"pasos" :"Para hacer esta receta primero pelar el pollo..."}, 
    "time": "00:15", 
    "ratings": [{"rating" : "1"}, {"rating" : "2"} ], 
    "category": "casera", 
    "ingredients": [
    { "ingredient" : "pollo", "cantidad" : "1"},
    { "ingredient" : "cebolla", "cantidad": "1"}]
    { "ingredient" : "aceite", "cantidad": "10ml"}]}

CABECERAS

El API acepta las cabeceras:
    
    Accept -> application/json o application/xml (Nos devuelve la respuesta en el formato solicitado)
    Accept-Language -> Para obtener los mensajes devueltos por el API en el idioma deseado: es para español,
                        en para inglés.

