
###Evaluacion final, curso 12 "Desarrollo de Aplicaciones Avanzadas en Android".

>Autor: **David Minaya**

>Fecha de creacion: **22 abril 2017**

>Ultima modificacion: **23 abril 2017**

####Descripcion
En este proyecto se desarrolla una aplicacion que implementa las
funcionalidades de login utilizando el SDK de Facebook y la
funcionalidad de anuncios utilizando el SDK de anuncios de Google.

####Integracion de los SDK

#####SDK de Facebook

######Paso 1
Para una sencilla integracion del SDK de Facebook agregamos la dependencia **+** en el archivo 
`build.gradle(Module app) > dependencies { + }`.
  
**+** `compile 'com.facebook.android:facebook-android-sdk:[4,5)'`

Ej:

```
dependencies {

    compile 'com.facebook.android:facebook-android-sdk:[4,5)

}
```

Luego precionamos `Sync Now` para aplicar los cambios e integrar el SDK de Facebook.

######Paso 2
En el archivo `string.xml` agregamos la etiqueta string **+**  con el nombre 
`"facebook_app_id"` y le asignamos como valor el identificador de la aplicacion. 

Para obtener el identificador de su aplicacion consulte la siguiente documentacion:

**+** `<string name="facebook_app_id">416687878699990</string>`

Ej:
```
<resources>

    <string name="facebook_app_id">416687878699990</string>

</resources>
```

######Paso 3

Por ultimo en la etiqueta `<application></application>` del archivo `AndroidManifest.xml` de 
nuestro proyecto agregamos la metadata:

```
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id">
</meta-data>
```

Con esto el SDK de Facebook quedara correctamente integrado en nuestro proyecto.

#####SDK de anuncios de Google

######Paso 1
Para una sencilla integracion del SDK de Facebook agregamos la dependencia **+** en el archivo 
`build.gradle(Module app) > dependencies { + }`.
  
**+** `compile 'com.google.android.gms:play-services-ads:10.2.1'`

Ej:

```
dependencies {

    compile 'com.google.android.gms:play-services-ads:10.2.1'

}
```

Luego precionamos `Sync Now` para aplicar los cambios e integrar el SDK de anuncios de Google.

######Paso 2

Por ultimo en la etiqueta `<application></application>` del archivo `AndroidManifest.xml` de 
nuestro proyecto agregamos la metadata:

```
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version">
</meta-data>
```

Con esto el SDK de anuncios de Google quedara correctamente integrado en nuestro proyecto.

Con los SDK correctamente integrados podemos pasar a la implementacion de las diferentes 
funcionalidades en nuestro proyecto, la funcionalidad de login utilizando el SDK de Facebook y la
funcionalidad de anuncios utilizando el SDK de anuncios de Google.

####Implementacion de las funcionalidades

#####Login con facebook
La manera mas sencilla de realizar el login con Facebook es agregando el boton de Facebook en el 
layout de nuestra actividad o fragmento e implementando los metodos de este boton.

Para agregar el boton de Facebook en el layout de nuestra actividad, utilizamos la etiqueta:

```
 <com.facebook.login.widget.LoginButton
        android:id="@+id/b_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"/>

```

Y lo enlazamos con nuestra actividad, como con cualquier otro widget:

```
LoginButton loginButton = (LoginButton)findViewById(R.id.b_login);
```

Despues pasamos a la implementacion de los metodos nescesarios para realizar el login.

Utilizamos el metodo `.setReadPermissions(String)` para solicitar los permiso necesarios para
acceder a los datos del usuario que inicie sesion. Aunque no implementemos este metodo por defecto
tendremos asignados los permisos de `public_profile` y `email`. Si necesitamos mas permisos 
tendremos que utilizar el metodo para solicitarlos, algunos de estos permisos y los datos a los que 
nos dan acceso son:

- `email` (email)
- `public_profile` (nombre, foto de portada, genero, zona horaria, localidad, etc...)
- `user_birthday`(fecha de cumpleaños)
- `user_photos` (fotos subidas y etiquetadas)
- `user_location` (Localizacion).

Dependiendo de los permisos que solicites tu aplicacion puede ser sometida a una revicion por 
par


`loginButton.setReadPermissions("email, user_birthday");`




  
