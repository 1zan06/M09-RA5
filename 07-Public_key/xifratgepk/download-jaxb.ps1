$libs = @(
    "https://repo1.maven.org/maven2/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar",
    "https://repo1.maven.org/maven2/com/sun/xml/bind/jaxb-impl/2.3.1/jaxb-impl-2.3.1.jar", 
    "https://repo1.maven.org/maven2/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar",
    "https://repo1.maven.org/maven2/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar"
)

mkdir lib -Force
foreach ($lib in $libs) {
    $filename = $lib.Split('/')[-1]
    Write-Host "Descargando $filename..."
    Invoke-WebRequest -Uri $lib -OutFile "lib\$filename"
}
Write-Host "¡Todas las librerías descargadas!"
