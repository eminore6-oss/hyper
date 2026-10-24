# HYPEROS A5 PRO

Launcher Android inspirado en HyperOS, pensado para Redmi A5. Proyecto inicial funcional con pantalla de inicio, cajón de aplicaciones, búsqueda, modo claro/oscuro, personalización de color y una app Temas con 100 temas integrados.

## Compilar APK
La forma más sencilla es subir el proyecto a GitHub y usar **Actions**:
1. Crea un repositorio vacío en GitHub.
2. Sube el contenido de este ZIP manteniendo la estructura de carpetas.
3. Abre la pestaña **Actions** y ejecuta `Build HYPEROS A5 PRO APK` si no se ejecuta automáticamente.
4. Descarga el artefacto `HYPEROS-A5-PRO-debug-apk` al finalizar el workflow.
5. Descomprime el artefacto y abre el APK en tu Redmi A5. Puede que debas autorizar instalar apps desde esa fuente.

## Funciones incluidas
- Launcher seleccionable como aplicación de inicio.
- Apps instaladas detectadas por Android y cajón con búsqueda.
- Modo claro/oscuro y color de acento persistentes.
- App Temas con 100 presets seleccionables para fondo de color, acento y estilo.
- Ajustes propios y acceso a ajustes de Android.
- Flujo GitHub Actions para compilar APK debug.

## Límites importantes
Es un launcher, no una ROM ni una sustitución completa de HyperOS. Android restringe que una app normal reemplace la pantalla de bloqueo del sistema, el panel de notificaciones, la barra de estado o controles privilegiados. El panel rápido real sigue siendo el de Android.

## Requisitos
Android Studio reciente, JDK 17 y Android SDK. CI usa Gradle 8.9 y Android Gradle Plugin 8.7.3.
