"""
Puebla tu proyecto de MockAPI con datos reales de Colombia:
redes de bicicletas por ciudad + estaciones con nombres de barrios/lugares reales.

CÓMO USARLO:
1. Instala la librería requests si no la tienes:
       pip install requests
2. Corre el script:
       python seed_mockapi_colombia.py
3. Espera a que termine (imprime el progreso en consola).
4. Verifica en el navegador:
       https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1/networks
       https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1/stations

Esto BORRA todos los registros actuales de "networks" y "stations"
en tu proyecto y los reemplaza por los datos de abajo.
"""

import requests

BASE_URL = "https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1"

# ---------------------------------------------------------------------------
# 1. Datos: redes de bicicletas por ciudad colombiana, con sus estaciones
#    (coordenadas aproximadas a lugares reales de cada ciudad).
# ---------------------------------------------------------------------------
REDES = [
    {
        "name": "EnCicla",
        "city": "Medellín",
        "country": "CO",
        "stations": [
            ("Parque Berrío", 6.2518, -75.5673),
            ("Universidad de Antioquia", 6.2676, -75.5658),
            ("El Poblado", 6.2087, -75.5679),
            ("Laureles", 6.2447, -75.5989),
            ("Envigado", 6.1719, -75.5847),
        ],
    },
    {
        "name": "Bogotá en Bici",
        "city": "Bogotá",
        "country": "CO",
        "stations": [
            ("Chapinero", 4.6483, -74.0648),
            ("Usaquén", 4.6947, -74.0300),
            ("La Candelaria", 4.5981, -74.0758),
            ("Zona Rosa", 4.6668, -74.0546),
            ("Suba", 4.7420, -74.0837),
        ],
    },
    {
        "name": "Cali Rueda",
        "city": "Cali",
        "country": "CO",
        "stations": [
            ("San Antonio", 3.4460, -76.5410),
            ("Granada", 3.4560, -76.5330),
            ("Ciudad Jardín", 3.3730, -76.5320),
            ("Unicentro", 3.3839, -76.5330),
        ],
    },
    {
        "name": "Barranquilla Bici",
        "city": "Barranquilla",
        "country": "CO",
        "stations": [
            ("El Prado", 10.9890, -74.7890),
            ("Riomar", 11.0090, -74.8280),
            ("Alto Prado", 10.9950, -74.8020),
            ("Boston", 10.9860, -74.7950),
        ],
    },
    {
        "name": "Cartagena en Bici",
        "city": "Cartagena",
        "country": "CO",
        "stations": [
            ("Centro Histórico", 10.4236, -75.5488),
            ("Bocagrande", 10.3986, -75.5555),
            ("Getsemaní", 10.4222, -75.5470),
            ("Manga", 10.4030, -75.5390),
        ],
    },
    {
        "name": "Bucaramanga Rueda",
        "city": "Bucaramanga",
        "country": "CO",
        "stations": [
            ("Cabecera del Llano", 7.1080, -73.1180),
            ("San Alonso", 7.1290, -73.1220),
            ("Cañaveral", 7.1140, -73.0980),
        ],
    },
    {
        "name": "Pereira Bici",
        "city": "Pereira",
        "country": "CO",
        "stations": [
            ("Circunvalar", 4.8090, -75.6950),
            ("Cuba", 4.7940, -75.6890),
            ("Pinares", 4.8070, -75.7040),
        ],
    },
    {
        "name": "Manizales en Bici",
        "city": "Manizales",
        "country": "CO",
        "stations": [
            ("Chipre", 5.0730, -75.5230),
            ("Palermo", 5.0640, -75.5090),
            ("Centro", 5.0680, -75.5170),
        ],
    },
    {
        "name": "Santa Marta Bici",
        "city": "Santa Marta",
        "country": "CO",
        "stations": [
            ("El Rodadero", 11.2020, -74.2260),
            ("Bavaria", 11.2280, -74.1980),
            ("Centro Histórico", 11.2410, -74.1990),
        ],
    },
    {
        "name": "Ibagué en Bici",
        "city": "Ibagué",
        "country": "CO",
        "stations": [
            ("Centro", 4.4390, -75.2320),
            ("Piedra Pintada", 4.4530, -75.2270),
            ("Ambalá", 4.4270, -75.2400),
        ],
    },
]

import random


def borrar_todos(recurso: str) -> None:
    """Borra todos los registros existentes de un recurso de MockAPI."""
    resp = requests.get(f"{BASE_URL}/{recurso}")
    resp.raise_for_status()
    registros = resp.json()
    print(f"Borrando {len(registros)} registros existentes de '{recurso}'...")
    for r in registros:
        requests.delete(f"{BASE_URL}/{recurso}/{r['id']}")
    print(f"'{recurso}' limpio.")


def poblar() -> None:
    borrar_todos("networks")
    borrar_todos("stations")

    for red in REDES:
        payload_red = {
            "name": red["name"],
            "city": red["city"],
            "country": red["country"],
        }
        resp = requests.post(f"{BASE_URL}/networks", json=payload_red)
        resp.raise_for_status()
        red_creada = resp.json()
        network_id = red_creada["id"]
        print(f"Red creada: {red['name']} ({red['city']}) -> id={network_id}")

        for nombre_estacion, lat, lon in red["stations"]:
            payload_estacion = {
                "name": nombre_estacion,
                "free_bikes": random.randint(0, 15),
                "empty_slots": random.randint(0, 15),
                "latitude": lat,
                "longitude": lon,
                "networkId": network_id,
            }
            requests.post(f"{BASE_URL}/stations", json=payload_estacion)

        print(f"  -> {len(red['stations'])} estaciones creadas para {red['name']}")

    print("\n¡Listo! Datos colombianos cargados en MockAPI.")


if __name__ == "__main__":
    poblar()
