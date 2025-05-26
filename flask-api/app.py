# app.py

# --- Importaciones necesarias ---
from flask import Flask, request, jsonify
from flask_cors import CORS
import requests
import random
from db import Usuario, session

# --- Configuración básica de Flask ---
app = Flask(__name__)
CORS(app)

# --- ENDPOINT: Obtener usuarios de la base de datos ---
@app.route("/usuarios")
def obtener_usuarios():
    try:
        usuarios = session.query(Usuario).all()
        return jsonify([{"id": u.id, "usuario": u.usuario} for u in usuarios])
    except Exception as e:
        return jsonify({"error": "Error al acceder a la base de datos", "detalle": str(e)}), 500

# --- ENDPOINT: Pruebas de errores simulados ---
@app.route("/api/test")
def test_api():
    tipo = request.args.get("tipo")
    if tipo == "saludo":
        return jsonify({"mensaje": "Hola desde Flask!"})
    elif tipo == "notfound":
        return jsonify({"error": "Recurso no encontrado"}), 404
    elif tipo == "servererror":
        raise Exception("Error interno simulado")
    else:
        return jsonify({"error": "Tipo de prueba no reconocido"}), 400

# --- ENDPOINT: Simula una API que devuelve el parámetro recibido ---
@app.route("/mi-api")
def mi_api():
    param = request.args.get("param", "no recibido")
    return jsonify({"respuesta": f"Parámetro recibido: {param}"})

# --- ENDPOINT: Lee un archivo y devuelve su contenido ---
@app.route("/leer-archivo")
def leer_archivo():
    nombre_archivo = request.args.get("nombre", "inexistente.txt")
    try:
        with open(nombre_archivo, "r") as f:
            contenido = f.read()
        return jsonify({"contenido": contenido})
    except FileNotFoundError:
        return jsonify({"error": f"Archivo '{nombre_archivo}' no encontrado"}), 404
    except Exception as e:
        return jsonify({"error": "Error al leer el archivo", "detalle": str(e)}), 500

# --- ENDPOINT: Consulta la PokeAPI y devuelve algunos datos del Pokémon ---
@app.route("/pokeapi")
def pokeapi():
    nombre = request.args.get("nombre", "pikachu")
    try:
        respuesta = requests.get(f"https://pokeapi.co/api/v2/pokemon/{nombre}")
        respuesta.raise_for_status()
        datos = respuesta.json()
        return jsonify({
            "nombre": datos["name"],
            "altura": datos["height"],
            "peso": datos["weight"],
            "tipo_principal": datos["types"][0]["type"]["name"]
        })
    except requests.exceptions.HTTPError:
        return jsonify({"error": f"No se encontró el Pokémon '{nombre}'"}), 404
    except Exception as e:
        return jsonify({"error": "Error al consultar la API externa", "detalle": str(e)}), 500

# --- ENDPOINT: Login básico usando la base de datos ---
@app.route("/login", methods=["POST"])
def login():
    datos = request.get_json()
    usuario = datos.get("usuario")
    contrasena = datos.get("contrasena")

    if not usuario or not contrasena:
        return jsonify({"error": "Faltan datos"}), 400

    usuario_encontrado = session.query(Usuario).filter_by(usuario=usuario, contrasena=contrasena).first()

    if usuario_encontrado:
        return jsonify({"mensaje": f"Bienvenido, {usuario}!"})
    else:
        return jsonify({"error": "Usuario o contraseña incorrectos"}), 401

# --- ENDPOINT NUEVO: Simulación personalizada para Spring Boot ---
@app.route("/simulacion")
def simulacion():
    resultado = random.choice([
        "Simulación completada con éxito",
        "Error crítico en el proceso",
        "Resultado incierto: repite la prueba"
    ])
    return jsonify({"resultado": resultado})

# --- Manejador global de errores inesperados ---
@app.errorhandler(Exception)
def manejar_errores(e):
    return jsonify({
        "tipo": type(e).__name__,
        "mensaje": str(e)
    }), 500

# --- Punto de entrada de la aplicación ---
if __name__ == "__main__":
    app.run(port=5000)
