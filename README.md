# CasaColina

## 📱 Descripción

Proyecto de creación de una aplicación móvil para el restaurante **"Casa Colina"**, desarrollado con **Kotlin** y **Jetpack Compose** utilizando **Android Studio**.

La aplicación presenta una experiencia digital para el restaurante, incluyendo navegación por la carta, detalle de platos, carrito de compras, reservas y perfil de usuario.

## 📥 Descargar el proyecto

Para obtener el código fuente de la aplicación:

1. Ingresa al repositorio de GitHub:
   **https://github.com/RodrigoZid/CasaColina**

2. Presiona el botón verde **Code**.

3. Selecciona **Download ZIP**.

4. Una vez descargado el archivo `.zip`, descomprímelo en una ubicación de tu computadora.

> ⚠️ **Importante:** Primero debes descomprimir el archivo ZIP antes de abrir el proyecto en Android Studio.

## 💻 Abrir el proyecto en Android Studio

1. Abre **Android Studio**.

2. Selecciona **Open**.

3. Busca la carpeta donde descomprimiste el proyecto.

4. Selecciona la carpeta raíz:

```text
CasaColinaApp/
```

5. Presiona **Open**.

6. Espera a que Android Studio realice la sincronización de Gradle y descargue las dependencias necesarias.

7. Si Android Studio solicita instalar algún componente o aceptar licencias, sigue las instrucciones indicadas.

## ▶️ Ejecutar la aplicación

Una vez finalizada la sincronización:

1. Conecta un dispositivo Android mediante USB con la **Depuración USB** activada, o inicia un emulador desde Android Studio.

2. Selecciona el dispositivo en la barra superior.

3. Presiona **Run ▶** o utiliza:

```text
Shift + F10
```

4. Android Studio compilará e instalará la aplicación en el dispositivo seleccionado.

## 🛠️ Tecnologías utilizadas

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Android Studio**
- **Navigation Compose**
- **Coil**
- **Gradle Kotlin DSL**

## 📂 Estructura del proyecto

```text
CasaColinaApp/
├── build.gradle.kts                 # Configuración principal del proyecto
├── settings.gradle.kts              # Configuración de Gradle
└── app/
    ├── build.gradle.kts             # Configuración del módulo app
    └── src/
        └── main/
            ├── AndroidManifest.xml
            └── java/com/casacolina/app/
                ├── MainActivity.kt
                │
                ├── ui/
                │   ├── theme/
                │   │   ├── Color.kt
                │   │   ├── Theme.kt
                │   │   └── Type.kt
                │   │
                │   ├── navigation/
                │   │   ├── NavItem.kt
                │   │   └── AppNavigation.kt
                │   │
                │   ├── components/
                │   │   ├── TopBar.kt
                │   │   └── BottomNavigationBar.kt
                │   │
                │   └── screens/
                │       ├── HomeScreen.kt
                │       ├── MenuScreen.kt
                │       ├── DishDetailScreen.kt
                │       ├── CartScreen.kt
                │       ├── CheckoutScreen.kt
                │       ├── ReservationsScreen.kt
                │       └── ProfileScreen.kt
                │
                └── data/
                    ├── model/
                    │   ├── Dish.kt
                    │   ├── Reservation.kt
                    │   └── CartItem.kt
                    │
                    └── repository/
                        └── MockData.kt
```

La estructura anterior corresponde a la organización del proyecto proporcionada para **CasaColinaApp**.

## 🧭 Funcionalidades principales

La aplicación cuenta con las siguientes secciones:

- 🏠 **Inicio**
- 🍽️ **Carta**
- 📋 **Detalle de platos**
- 🛒 **Carrito**
- 💳 **Checkout**
- 📅 **Reservas**
- 👤 **Perfil**

La navegación entre estas secciones se encuentra implementada mediante **Navigation Compose**.

## 🎨 Diseño

La aplicación utiliza **Material 3** y un sistema de colores personalizado para representar la identidad visual de Casa Colina.

Entre los colores definidos se encuentran tonos terracota, crema, dorado y verde.

## 🍽️ Datos de ejemplo

Actualmente el proyecto utiliza datos simulados (*Mock Data*) para representar los platos del restaurante.

Entre los platos incluidos se encuentran:

- Lomo Saltado al Wok
- Arroz con Pato Chiclayano
- Ceviche Clásico del Pacífico

Los datos incluyen información como nombre, descripción, precio, categoría, calificación, cantidad de reseñas e imagen.

## 📋 Requisitos

Para ejecutar el proyecto se recomienda contar con:

- Android Studio
- Android SDK
- JDK 17
- Un dispositivo Android o emulador

El proyecto está configurado con `compileSdk 35`, `targetSdk 35` y `minSdk 26`.

## 👥 Trabajo con el repositorio

Si deseas realizar cambios en el proyecto:

1. Descarga o clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Realiza los cambios necesarios.
4. Prueba la aplicación.
5. Crea un **Commit** con los cambios.
6. Realiza **Push** para enviar los cambios a GitHub.

## 📄 Licencia

Este proyecto ha sido desarrollado con fines académicos.