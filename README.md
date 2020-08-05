![](https://billers-images.s3.amazonaws.com/Budgie_horizontal_blanco-size.png)
# hubble
Tool to register microservices. This project is based on the **Service Discovery** architecture pattern 

# Service Registry
Clientes de un servicio usan cualquier patrón Client-Side Discovery o Server-side Discovery para determinar la ubicación de una instancia a los cuales se pueda enviar peticiones.

- Cada instancias de un servicio expone un API como HTTP/REST en una ubicación en particular (host y puerto). 
- El número de instancias y sus ubicaciones cambian dinámicamente, cuando se usan maquinar virtuales y contenedores usualmente se asignan IP´s dinámicamente. **Por ejemplo EC2 ajusta el numero de instancias basada en la carga**

Implementar un *service registry*. Cada instancias de un servicio será registrado durante la fase de inicio y se tiene que desuscribir en la fase de apagado.
El cliente de un servicio y/o enrutadores consultan el registro del servicio para encontrar las instancias disponibles

# Beneficios
El cliente de un servicio y/o enrutadores pueden descurbir la ubicación de los servicios.

# Inconvenientes
El Service Registry en un componente principal del sistema, a medida que es usado por el cliente cuando el Service Registry falla podrian surgir inconvenientes importantes.
