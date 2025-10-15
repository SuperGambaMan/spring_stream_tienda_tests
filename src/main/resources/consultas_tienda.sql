INSERT INTO fabricante VALUES(1, 'Asus');
INSERT INTO fabricante VALUES(2, 'Lenovo');
INSERT INTO fabricante VALUES(3, 'Hewlett-Packard');
INSERT INTO fabricante VALUES(4, 'Samsung');
INSERT INTO fabricante VALUES(5, 'Seagate');
INSERT INTO fabricante VALUES(6, 'Crucial');
INSERT INTO fabricante VALUES(7, 'Gigabyte');
INSERT INTO fabricante VALUES(8, 'Huawei');
INSERT INTO fabricante VALUES(9, 'Xiaomi');

INSERT INTO producto VALUES(1, 'Disco duro SATA3 1TB', 86.99, 5);
INSERT INTO producto VALUES(2, 'Memoria RAM DDR4 8GB', 120, 6);
INSERT INTO producto VALUES(3, 'Disco SSD 1 TB', 150.99, 4);
INSERT INTO producto VALUES(4, 'GeForce GTX 1050Ti', 185, 7);
INSERT INTO producto VALUES(5, 'GeForce GTX 1080 Xtreme', 755, 6);
INSERT INTO producto VALUES(6, 'Monitor 24 LED Full HD', 202, 1);
INSERT INTO producto VALUES(7, 'Monitor 27 LED Full HD', 245.99, 1);
INSERT INTO producto VALUES(8, 'Portátil Yoga 520', 559, 2);
INSERT INTO producto VALUES(9, 'Portátil Ideapd 320', 444, 2);
INSERT INTO producto VALUES(10, 'Impresora HP Deskjet 3720', 59.99, 3);
INSERT INTO producto VALUES(11, 'Impresora HP Laserjet Pro M26nw', 180, 3);

-- ----------------------------------------------------------------------------

-- 1. Lista los nombres y los precios de todos los productos de la tabla producto CORREGIDO
SELECT nombre, precio
FROM producto;

-- 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares . CORREGIDO
SELECT nombre, ROUND(precio*1.18,2)
FROM producto;

-- 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula. CORREGIDO
SELECT UPPER(nombre), precio
FROM producto;

-- 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante. CORREGIDO
SELECT nombre, UPPER(SUBSTR(nombre,1,2))
FROM fabricante;

-- 5. Lista el código de los fabricantes que tienen productos.
SELECT DISTINCT  f.codigo
FROM fabricante f
    INNER JOIN producto p on f.codigo=p.codigo_fabricante;

-- 6. Lista los nombres de los fabricantes ordenados de forma descendente.
SELECT nombre
FROM fabricante
ORDER BY nombre DESC;

-- 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
SELECT nombre, precio
FROM producto
ORDER BY nombre ASC, precio DESC;

-- 8. Devuelve una lista con los 5 primeros fabricantes.
SELECT nombre
FROM fabricante
LIMIT 5;

-- 9. Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
SELECT nombre
FROM fabricante
ORDER BY codigo
LIMIT 2 OFFSET 3;

-- 10. Lista el nombre y el precio del producto más barato
SELECT nombre, precio
FROM producto
ORDER BY precio ASC
LIMIT 1;

-- 11. Lista el nombre y el precio del producto más caro
SELECT nombre, precio
FROM producto
ORDER BY precio DESC
LIMIT 1;

-- 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
SELECT nombre
FROM producto
WHERE codigo_fabricante = 2;

-- 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
SELECT nombre
FROM producto
WHERE precio <= 120;

-- 14. Lista los productos que tienen un precio mayor o igual a 400€.
SELECT nombre, precio
FROM producto
WHERE precio >= 400;

-- 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
SELECT nombre, precio
FROM producto
WHERE precio BETWEEN 80 AND 300;

-- 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
SELECT nombre, precio
FROM producto
WHERE precio > 200 AND codigo_fabricante = 6;

-- 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
SELECT nombre, precio
FROM producto
WHERE codigo_fabricante IN (1, 3, 5);

-- 18. Lista el nombre y el precio de los productos en céntimos.
SELECT nombre, precio * 100 AS precio_centimos
FROM producto;

-- 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
SELECT nombre
FROM fabricante
WHERE nombre LIKE 'S%';

-- 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
SELECT nombre, precio
FROM producto
WHERE nombre LIKE '%Portátil%';

-- 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
SELECT nombre, precio
FROM producto
WHERE nombre LIKE '%Monitor%' AND precio < 215;

-- 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
SELECT nombre, precio
FROM producto
WHERE precio >= 180
ORDER BY precio DESC, nombre ASC;

-- 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. Ordene el resultado por el nombre del fabricante, por orden alfabético.
SELECT p.nombre AS producto, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
ORDER BY f.nombre ASC;

-- 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
SELECT p.nombre AS producto, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
ORDER BY p.precio DESC
LIMIT 1;

-- 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
SELECT p.nombre, p.precio
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE f.nombre = 'Crucial' AND p.precio > 200;

-- 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate.
SELECT p.nombre, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE f.nombre IN ('Asus', 'Hewlett-Packard', 'Seagate');

-- 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
SELECT p.nombre AS producto, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE p.precio >= 180
ORDER BY p.precio DESC, p.nombre ASC;

-- 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
SELECT f.nombre AS fabricante, p.nombre AS producto
FROM fabricante f
    LEFT JOIN producto p ON f.codigo = p.codigo_fabricante
ORDER BY f.nombre ASC;

-- 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
SELECT f.nombre
FROM fabricante f
    LEFT JOIN producto p ON f.codigo = p.codigo_fabricante
WHERE p.codigo_fabricante IS NULL;

-- 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
SELECT COUNT(*) AS total_productos
FROM producto;

-- 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
SELECT COUNT(DISTINCT codigo_fabricante) AS num_fabricantes_con_productos
FROM producto;

-- 32. Calcula la media del precio de todos los productos
SELECT AVG(precio) AS media_precio
FROM producto;

-- 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
SELECT MIN(precio) AS precio_minimo
FROM producto;

-- 34. Calcula la suma de los precios de todos los productos.
SELECT SUM(precio) AS suma_precios
FROM producto;

-- 35. Calcula el número de productos que tiene el fabricante Asus.
SELECT COUNT(*) AS num_productos
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE f.nombre = 'Asus';

-- 36. Calcula la media del precio de todos los productos del fabricante Asus.
SELECT AVG(p.precio) AS media_precio
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE f.nombre = 'Asus';

-- 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
SELECT MAX(p.precio) AS precio_max,
       MIN(p.precio) AS precio_min,
       AVG(p.precio) AS precio_medio,
       COUNT(*) AS total_productos
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE f.nombre = 'Crucial';

-- 38. Muestra el número total de productos que tiene cada uno de los fabricantes. El listado también debe incluir los fabricantes que no tienen ningún producto. El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. Ordene el resultado descendentemente por el número de productos.
SELECT f.nombre, COUNT(p.codigo) AS num_productos
FROM fabricante f
    LEFT JOIN producto p ON f.codigo = p.codigo_fabricante
GROUP BY f.nombre
ORDER BY num_productos DESC;

-- 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
SELECT f.nombre AS fabricante,
       MAX(p.precio) AS precio_max,
       MIN(p.precio) AS precio_min,
       AVG(p.precio) AS precio_medio
FROM fabricante f
    LEFT JOIN producto p ON f.codigo = p.codigo_fabricante
GROUP BY f.nombre;

-- 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
SELECT codigo_fabricante,
       MAX(precio) AS precio_max,
       MIN(precio) AS precio_min,
       AVG(precio) AS precio_medio,
       COUNT(*) AS total_productos
FROM producto
GROUP BY codigo_fabricante
HAVING AVG(precio) > 200;

-- 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
SELECT f.nombre
FROM fabricante f
    JOIN producto p ON f.codigo = p.codigo_fabricante
GROUP BY f.nombre
HAVING COUNT(*) >= 2;

-- 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. Ordenado de mayor a menor número de productos.
SELECT f.nombre, COUNT(*) AS num_productos
FROM fabricante f
    JOIN producto p ON f.codigo = p.codigo_fabricante
WHERE p.precio >= 220
GROUP BY f.nombre
ORDER BY num_productos DESC;

-- 43. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
SELECT f.nombre
FROM fabricante f
    JOIN producto p ON f.codigo = p.codigo_fabricante
GROUP BY f.nombre
HAVING SUM(p.precio) > 1000;

-- 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €. Ordenado de menor a mayor por cuantía de precio de los productos.
SELECT f.nombre
FROM fabricante f
    JOIN producto p ON f.codigo = p.codigo_fabricante
GROUP BY f.nombre
HAVING SUM(p.precio) > 1000
ORDER BY SUM(p.precio) ASC;

-- 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
SELECT p.nombre AS producto, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE p.precio = (
    SELECT MAX(p2.precio)
    FROM producto p2
    WHERE p2.codigo_fabricante = f.codigo
)
ORDER BY f.nombre ASC;

-- 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante. Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
SELECT p.nombre AS producto, p.precio, f.nombre AS fabricante
FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
WHERE p.precio >= (
    SELECT AVG(p2.precio)
    FROM producto p2
    WHERE p2.codigo_fabricante = f.codigo
)
ORDER BY f.nombre ASC, p.precio DESC;

