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
SELECT nombre, precio FROM producto;

-- 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares . CORREGIDO
SELECT nombre, ROUND(precio*1.18,2) FROM producto;

-- 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula. CORREGIDO
SELECT UPPER(nombre), precio FROM producto;

-- 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros
-- caracteres del nombre del fabricante. CORREGIDO
SELECT nombre, UPPER(SUBSTR(nombre,1,2)) FROM fabricante;

-- 5. Lista el código de los fabricantes que tienen productos.
SELECT DISTINCT  f.codigo FROM fabricante f INNER JOIN producto p on f.codigo=p.codigo;

-- 6. Lista los nombres de los fabricantes ordenados de forma descendente.
-- 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
-- 8. Devuelve una lista con los 5 primeros fabricantes.
-- 9. Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
-- 10. Lista el nombre y el precio del producto más barato
-- 11. Lista el nombre y el precio del producto más caro
-- 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
-- 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
-- 14. Lista los productos que tienen un precio mayor o igual a 400€.
-- 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
-- 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
-- 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
-- 18. Lista el nombre y el precio de los productos en céntimos.
-- 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
-- 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
-- 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
-- 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
-- 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. Ordene el resultado por el nombre del fabricante, por orden alfabético.
-- 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
-- 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
-- 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate.
-- 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
-- 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
-- 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
-- 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
-- 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
-- 32. Calcula la media del precio de todos los productos
-- 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
-- 34. Calcula la suma de los precios de todos los productos.
-- 35. Calcula el número de productos que tiene el fabricante Asus.
-- 36. Calcula la media del precio de todos los productos del fabricante Asus.
-- 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
-- 38. Muestra el número total de productos que tiene cada uno de los fabricantes. El listado también debe incluir los fabricantes que no tienen ningún producto. El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. Ordene el resultado descendentemente por el número de productos.
-- 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
-- 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
-- 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
-- 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. Ordenado de mayor a menor número de productos.
-- 43. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
-- 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €. Ordenado de menor a mayor por cuantía de precio de los productos.
-- 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
-- 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante. Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
