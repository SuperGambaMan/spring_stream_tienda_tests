package org.iesvdm.tienda;

import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;


@Nested
@SpringBootTest
class TiendaApplicationTests {

	@Autowired
	FabricanteRepository fabRepo;
	
	@Autowired
	ProductoRepository prodRepo;

	@Test
	void testAllFabricante() {
		var listFabs = fabRepo.findAll();
		
		listFabs.forEach(f -> {
			System.out.println(">>"+f+ ":");
			f.getProductos().forEach(System.out::println);
		});
	}
	
	@Test
	void testAllProducto() {
		var listProds = prodRepo.findAll();

		listProds.forEach( p -> {
			System.out.println(">>"+p+":"+"\nProductos mismo fabricante "+ p.getFabricante());
			p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>"+pF));
		});
				
	}

	
	/** CORREGIDO
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */ 
	@Test
	void test1() {
		var listProds = prodRepo.findAll();
		//Se generan dos var
		var listNomPrec = listProds.stream()
			.map ((p)-> "Nombre: "+p.getNombre()+" Precio: "+p.getPrecio()+"€")
			.toList();
		listNomPrec.forEach(x->System.out.println(x));
		//Este es mas simple
		/* listProds.stream()
			.forEach (
				(p)-> System.out.println("Nombre: "+p.getNombre()+" Precio: "+p.getPrecio())
		); */

		Assertions.assertEquals(11, listNomPrec.size());
		Assertions.assertTrue(listNomPrec.contains("Nombre: Disco duro SATA3 1TB Precio: 86.99€"));
	}

	
	
	/** CORREGIDO
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		var listProds = prodRepo.findAll();

		double valorDollar = 1.18; //1 euro = 1.18 dolares aprox
		var listNomPrecDollar = listProds.stream() //Producto
			/* .map ((p)-> p.getPrecio()*valorDollar) //Double con precision double
			.map (prec -> BigDecimal.valueOf(prec).setScale(2,RoundingMode.HALF_UP)) // BigDecimal con 2 decimales
			.map (prec -> prec + "$") //String */ //SE PUEDE HACER EN UNA SOLA LINEA 
			.map (prod -> prod.getNombre()
									+" con precio "
									+BigDecimal.valueOf(prod.getPrecio()*valorDollar)
															.setScale(2,RoundingMode.HALF_UP)
															+"$")
				//String con nombre y precio en dolares
			.toList();
		listNomPrecDollar.forEach(s->System.out.println(s));

		Assertions.assertEquals(11, listNomPrecDollar.size());
		Assertions.assertTrue(listNomPrecDollar.contains("Impresora HP Laserjet Pro M26nw con precio 212.40$"));
	}
	
	/** CORREGIDO
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		var listProds = prodRepo.findAll();

		var listNomMayusPrec = listProds.stream()
			.map ((p)-> p.getNombre().toUpperCase() +" Precio: "+p.getPrecio()+"€")//String con nombre en mayusculas y precio
			.toList();
		listNomMayusPrec.forEach(s->System.out.println(s));

		Assertions.assertEquals(11, listNomMayusPrec.size());
		Assertions.assertTrue(listNomMayusPrec.contains("DISCO DURO SATA3 1TB Precio: 86.99€"));
		
	}
	
	/** CORREGIDO
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
		var listFabs = fabRepo.findAll();
		
		var listFabsMayus = listFabs.stream() //Fabricante
			.map ( f -> f.getNombre() + " " + f.getNombre()
												.substring(0,2)//coge las 2 primeras posiciones
												.toUpperCase())//String con nombre en mayusculas
			.toList();
		listFabsMayus.forEach(s->System.out.println(s));

		Assertions.assertEquals(9, listFabsMayus.size());
		Assertions.assertTrue(listFabsMayus.contains("Asus AS"));
	}
	
	/** CORREGIDO
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
		var listFabs = fabRepo.findAll();

        var listCodFabs = listFabs.stream() //Fabricante
                .filter (fabricante -> fabricante.getProductos() != null
                        && !fabricante.getProductos().isEmpty()) //Filtra por fabricantes que tengan la coleccion y no este vacia
                .map ( f -> f.getCodigo() )
                .toList();
        listCodFabs.forEach(s->System.out.println(s));

        Assertions.assertEquals(7, listCodFabs.size());
        Assertions.assertTrue(listCodFabs.contains(3));
	}
	
	/** CORREGIDO
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
		var listFabs = fabRepo.findAll();

        var listFabsDesc = listFabs.stream() //Fabricante
                .sorted(comparing((Fabricante f) -> f.getNombre(),reverseOrder()))
                .map ( f -> f.getNombre())
                .toList();
        listFabsDesc.forEach(s->System.out.println(s));

        Assertions.assertEquals(9, listFabsDesc.size());
        Assertions.assertTrue(listFabsDesc.contains("Asus"));
	}
	
	/** CORREGIDO
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
		var listProds = prodRepo.findAll();

        var listProdAscPreDesc = listProds.stream() //Producto
                .sorted(comparing((Producto p)-> p.getNombre())
                    .thenComparing((Producto p)->p.getPrecio(),reverseOrder()))
                .map ( p -> p.getNombre() )
                .toList();
        listProdAscPreDesc.forEach(s->System.out.println(s));

        Assertions.assertEquals(11, listProdAscPreDesc.size());
        //Assertions.assertTrue(listProdAscPreDesc.contains("Asus"));
	}
	
	/** CORREGIDO
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
		var listFabs = fabRepo.findAll();

        var list5Fabs = listFabs.stream() //Producto
                .limit(5)
                .map ( f -> f.getNombre() )
                .toList();
        list5Fabs.forEach(s->System.out.println(s));

        Assertions.assertEquals(5, list5Fabs.size());
	}
	
	/** CORREGIDO
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
		var listFabs = fabRepo.findAll();

        var listFabs4Mas = listFabs.stream() //Producto
                .skip(3)
                .limit(3)
                .map ( f -> f.getNombre() )
                .toList();
        listFabs4Mas.forEach(s->System.out.println(s));

        Assertions.assertEquals(3, listFabs4Mas.size());
	}
	
	/** CORREGIDO
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
		var listProds = prodRepo.findAll();

        var listProdBar = listProds.stream() //Producto
                .sorted(comparing((Producto p)-> p.getPrecio()))
                .limit(1)
                .map ( p -> p.getNombre() + " " + p.getPrecio() + "€")
                .toList();
        listProdBar.forEach(s->System.out.println(s));

        Assertions.assertEquals(1, listProdBar.size());
        Assertions.assertTrue(listProdBar.contains("Impresora HP Deskjet 3720 59.99€"));
	}
	
	/** CORREGIDO
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
		var listProds = prodRepo.findAll();

        /** var listProdCar = listProds.stream() //Producto //DEBERIA ser Optional<Producto>
                .sorted(comparing((Producto p)-> p.getPrecio(),reverseOrder()))//.findFirst() [Tiene que venir de Optional] para encontrar el primero
                .limit(1) //Limitamos en 1 para coger solo 1 (el más caro)
                .map ( p -> p.getNombre() + " " + p.getPrecio() + "€")
                .toList();*/
        Optional<Producto> listProdCar = listProds.stream()
                .sorted(
                        comparing(p-> p.getPrecio(), reverseOrder())
                ).findFirst();
        if(listProdCar.isPresent()){
            //listProdCar.isEmpty()
            Producto prod = listProdCar.get();
            System.out.println(prod.getNombre()+" "+prod.getPrecio());
        }

        listProdCar.ifPresent(s -> System.out.println(s.getNombre()+" "+s.getPrecio()));

        //listProdCar.forEach(s->System.out.println(s));
        //System.out.println(prod);

        //Assertions.assertEquals(1, listProdCar.size());
        //Assertions.assertTrue(listProdCar.if("GeForce GTX 1080 Xtreme 755.0€"));
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
		var listProds = prodRepo.findAll();

        var listProdCar = listProds.stream() //Producto
                .filter(p -> p.getFabricante() != null && p.getFabricante().getCodigo() == 2)
                .map ( p -> p.getNombre())
                .toList();
        listProdCar.forEach(s->System.out.println(s));

        Assertions.assertEquals(2, listProdCar.size());
        Assertions.assertTrue(listProdCar.contains("Portátil Ideapd 320"));
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
		var listProds = prodRepo.findAll();

        var listProd120Menos = listProds.stream() //Producto
                .filter(p -> p.getPrecio() <= 120)
                .map ( p -> p.getNombre() )
                .toList();
        listProd120Menos.forEach(s->System.out.println(s));

        Assertions.assertEquals(3, listProd120Menos.size());
        Assertions.assertTrue(listProd120Menos.contains("Disco duro SATA3 1TB"));
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
		var listProds = prodRepo.findAll();

        var listProd400Mas = listProds.stream() //Producto
                .filter(p -> p.getPrecio() >= 400)
                .map ( p -> p.getNombre() )
                .toList();
        listProd400Mas.forEach(s->System.out.println(s));

        Assertions.assertEquals(3, listProd400Mas.size());
        Assertions.assertTrue(listProd400Mas.contains("GeForce GTX 1080 Xtreme"));
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
		var listProds = prodRepo.findAll();

        var listProd80a300 = listProds.stream() //Producto
                .filter(p -> p.getPrecio() >= 80 && p.getPrecio() <= 300)
                .map ( p -> p.getNombre() )
                .toList();
        listProd80a300.forEach(s->System.out.println(s));

        Assertions.assertEquals(7, listProd80a300.size());
        Assertions.assertTrue(listProd80a300.contains("Monitor 27 LED Full HD"));
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
		var listProds = prodRepo.findAll();

        var listProd200MasCod6 = listProds.stream() //Producto
                .filter(p -> p.getPrecio() >= 200)
                .filter(p -> p.getFabricante() != null && p.getFabricante().getCodigo() == 6)
                .map ( p -> p.getNombre() )
                .toList();
        listProd200MasCod6.forEach(s->System.out.println(s));

        Assertions.assertEquals(1, listProd200MasCod6.size());
        Assertions.assertTrue(listProd200MasCod6.contains("GeForce GTX 1080 Xtreme"));
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
		var listProds = prodRepo.findAll();

        Set<Integer> SetCodigosFiltrar = new HashSet<>(Arrays.asList(1, 3, 5));
        var listProdCod1o3o5 = listProds.stream() //Producto
                .filter(p -> p.getFabricante() != null && SetCodigosFiltrar.contains(p.getFabricante().getCodigo()))
                .map ( p -> p.getNombre() )
                .toList();
        listProdCod1o3o5.forEach(s->System.out.println(s));

        Assertions.assertEquals(5, listProdCod1o3o5.size());
        Assertions.assertTrue(listProdCod1o3o5.contains("Disco duro SATA3 1TB"));
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
		var listProds = prodRepo.findAll();

        var listProdPrecCent = listProds.stream() //Producto
                .map ( p -> p.getNombre() + " " + ((int)p.getPrecio()*100) )
                .toList();
        listProdPrecCent.forEach(s->System.out.println(s));

        Assertions.assertEquals(11, listProdPrecCent.size());
        Assertions.assertTrue(listProdPrecCent.contains("Memoria RAM DDR4 8GB 12000"));
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
		var listFabs = fabRepo.findAll();

        var listFabsPorS = listFabs.stream() //Producto
                .filter(f -> f.getNombre().startsWith("S"))
                .map ( f -> f.getNombre() )
                .toList();
        listFabsPorS.forEach(s->System.out.println(s));

        Assertions.assertEquals(2, listFabsPorS.size());
        Assertions.assertTrue(listFabsPorS.contains("Samsung"));
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
		var listProds = prodRepo.findAll();

        var listProdPortatil = listProds.stream() //Producto
                .filter(p -> p.getNombre().contains("Portátil"))
                .map ( p -> p.getNombre())
                .toList();
        listProdPortatil.forEach(s->System.out.println(s));

        Assertions.assertEquals(2, listProdPortatil.size());
        Assertions.assertTrue(listProdPortatil.contains("Portátil Ideapd 320"));
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
		var listProds = prodRepo.findAll();

        var listProdMonitor215 = listProds.stream() //Producto
                .filter(p -> p.getNombre().contains("Monitor") && p.getPrecio() < 215)
                .map ( p -> p.getNombre())
                .toList();
        listProdMonitor215.forEach(s->System.out.println(s));

        Assertions.assertEquals(1, listProdMonitor215.size());
        Assertions.assertTrue(listProdMonitor215.contains("Monitor 24 LED Full HD"));
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
    @Test
	void test22() {
		var listProds = prodRepo.findAll();

        var listPreDescProdAsc180 = listProds.stream() //Producto
                .filter(p -> p.getPrecio() >= 180)
                .sorted(comparing(( Producto p)->p.getPrecio(),reverseOrder())
                        .thenComparing((Producto p)-> p.getNombre()))
                .map ( p -> p.getNombre() + " " + p.getPrecio() )
                .toList();
        listPreDescProdAsc180.forEach(s->System.out.println(s));

        Assertions.assertEquals(7, listPreDescProdAsc180.size());
        Assertions.assertTrue(listPreDescProdAsc180.contains("Portátil Yoga 520 559.0"));
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		var listProds = prodRepo.findAll();

        var listProdsPreFabs = listProds.stream() //Producto
                .sorted(comparing(( Producto p)->p.getFabricante().getNombre()))
                .map ( p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre() )
                .toList();
        listProdsPreFabs.forEach(s->System.out.println(s));

        Assertions.assertEquals(11, listProdsPreFabs.size());
        Assertions.assertTrue(listProdsPreFabs.contains("GeForce GTX 1050Ti 185.0 Gigabyte"));
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		var listProds = prodRepo.findAll();

        var listProdsPreFabs = listProds.stream() //Producto
                .sorted(comparing(( Producto p)->p.getFabricante().getNombre()))
                .limit(1) //Limitamos en 1 para coger solo 1 (el más caro)
                .map ( p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre() )
                .toList();
        listProdsPreFabs.forEach(s->System.out.println(s));

        Assertions.assertEquals(11, listProdsPreFabs.size());
        Assertions.assertTrue(listProdsPreFabs.contains("GeForce GTX 1050Ti 185.0 Gigabyte"));
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		var listProds = prodRepo.findAll();

        var listProdsCrucial = listProds.stream()
                .filter(p->p.getFabricante().getNombre().equals("Crucial") && p.getPrecio() > 200)
                .map(p -> p.getNombre())
                .toList();
        listProdsCrucial.forEach(s->System.out.println(s));

        Assertions.assertEquals(1, listProdsCrucial.size());
        Assertions.assertTrue(listProdsCrucial.contains("GeForce GTX 1080 Xtreme"));
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		var listProds = prodRepo.findAll();

        var listProdsAsusHPSea = listProds.stream()
                .filter(p->p.getFabricante().getNombre().equals("Asus")
                        || p.getFabricante().getNombre().equals("Hewlett-Packard")
                        || p.getFabricante().getNombre().equals("Seagate"))
                .map(p -> p.getNombre())
                .toList();
        listProdsAsusHPSea.forEach(s->System.out.println(s));

        Assertions.assertEquals(5, listProdsAsusHPSea.size());
        Assertions.assertTrue(listProdsAsusHPSea.contains("Monitor 27 LED Full HD"));
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		  //     REVISAR
	@Test
	void test27() {
		var listProds = prodRepo.findAll();

        /* Ejercicio resuelto
        // Filtrar productos con precio >= 180, ordenar y formatear tabla
        List<Producto> filtrados = listProds.stream()
                .filter(p -> p.getPrecio() >= 180)
                .sorted(
                        Comparator.comparing(Producto::getPrecio).reversed()
                                .thenComparing(Producto::getNombre)
                )
                .toList();

        // Calcular longitudes máximas de columnas
        int maxNombre = Math.max("Producto".length(),
                filtrados.stream().mapToInt(p -> p.getNombre().length()).max().orElse(0));
        int maxPrecio = Math.max("Precio".length(),
                filtrados.stream().mapToInt(p -> String.valueOf(p.getPrecio()).length()).max().orElse(0));
        int maxFabricante = Math.max("Fabricante".length(),
                filtrados.stream().mapToInt(p -> p.getFabricante().getNombre().length()).max().orElse(0));

        // Cabecera
        System.out.printf("%-" + maxNombre + "s | %-" + maxPrecio + "s | %s%n",
                "Producto", "Precio", "Fabricante");
        System.out.println("-".repeat(maxNombre + maxPrecio + maxFabricante + 6));

        // Filas
        filtrados.forEach(p ->
                System.out.printf("%-" + maxNombre + "s | %-" + maxPrecio + "s | %s%n",
                        p.getNombre(), p.getPrecio(), p.getFabricante().getNombre())
        );*/

        long maxLongNombre = listProds.stream().mapToLong(p -> p.getNombre().length()).max().orElse(0);

        long maxLongPrecio = listProds.stream().mapToLong(p -> BigDecimal.valueOf(p.getPrecio())
                                                    .setScale(2,RoundingMode.HALF_UP)
                                                    .toString()
                                                    .length())
                                                .max()
                                                .orElse(0);

        //String Cabecera = "Producto" + (maxLongNombre * " ") + " Precio ";



        String cuerpoTabla = listProds.stream()
                        .filter(p->p.getPrecio() >=180)
                                .sorted(comparing((Producto p)->p.getPrecio(),reverseOrder())
                                        .thenComparing((Producto p) -> p.getNombre() ))

                                .map(p -> p.getNombre()
                                        + " ".repeat((int)maxLongNombre - p.getNombre().length())
                                        +"|"
                                        +BigDecimal.valueOf(p.getPrecio())
                                                                .setScale(2,RoundingMode.HALF_UP)
                                        +" ".repeat( (int)maxLongPrecio - BigDecimal.valueOf(p.getPrecio())
                                                    .toString()
                                                    .length())
                                        +"|"
                                        +p.getFabricante().getNombre()
                                )
                                .collect(joining("\n"));

        System.out.println(cuerpoTabla);

        Assertions.assertEquals(7, cuerpoTabla.length());
        //Assertions.assertTrue(filtrados.contains("GeForce GTX 1080 Xtreme         | 755.0  | Crucial"));

	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */               //  REVISAR
	@Test
	void test28() {
		var listFabs = fabRepo.findAll();
        var listProds = prodRepo.findAll();

        // Agrupamos productos por nombre de fabricante
        Map<String, List<String>> productosPorFabricante =
                listProds.stream()
                        .collect(groupingBy(
                                p -> p.getFabricante().getNombre(),
                                mapping(p -> p.getNombre(), toList())
                        ));

        // Mostramos todos los fabricantes, incluso los sin productos
        listFabs.stream()
                .sorted(Comparator.comparing(Fabricante::getNombre))
                .forEach(f -> {
                    System.out.println("Fabricante: " + f.getNombre() + "\n");
                    System.out.println("\t\tProductos:");
                    productosPorFabricante.getOrDefault(f.getNombre(), List.of())
                            .forEach(p -> System.out.println("\t\t" + p));
                    System.out.println();
                });

        Assertions.assertEquals(7, productosPorFabricante.size());
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
		var listFabs = fabRepo.findAll();

        var fabricantesSinProductos = listFabs.stream()
                .filter(f ->
                        f.getProductos().stream()
                                .noneMatch(p -> p.getFabricante().getNombre().equals(f.getNombre()))
                )
                .map(f -> f.getNombre())
                .toList();

        fabricantesSinProductos.forEach(System.out::println);

        Assertions.assertEquals(2, fabricantesSinProductos.size());
        Assertions.assertTrue(fabricantesSinProductos.contains("Huawei"));
    }
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
		var listProds = prodRepo.findAll();

        var totalProductos = listProds.stream()
                .count();
        System.out.println(totalProductos);

        Assertions.assertEquals(11, listProds.size());
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
		var listProds = prodRepo.findAll();

        long numFabsConProds = listProds.stream()
                .map(p -> p.getFabricante().getCodigo())
                .distinct()
                .count();

        System.out.println(numFabsConProds);

        Assertions.assertEquals(7,numFabsConProds);
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
        var listProds = prodRepo.findAll();

        OptionalDouble media = listProds.stream()
                .mapToDouble(p -> p.getPrecio())
                .average();

        System.out.println(media.getAsDouble());

        Assertions.assertEquals( 271.7236363636364,media.getAsDouble());
    }
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
		var listProds = prodRepo.findAll();

        var precioMinimo = listProds.stream()
                .mapToDouble(p->p.getPrecio())
                .min();

        System.out.println(precioMinimo.getAsDouble());

        Assertions.assertEquals( 59.99,precioMinimo.getAsDouble());
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		var listProds = prodRepo.findAll();

        var sumaProductos = listProds.stream()
                .mapToDouble(p->p.getPrecio())
                .sum();

        System.out.println(sumaProductos);

         Assertions.assertEquals( 2988.96,sumaProductos);

	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		var listProds = prodRepo.findAll();

        var productosAsus = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Asus"))
                .map(p -> p.getNombre())
                .toList();

        productosAsus.forEach(System.out::println);

        Assertions.assertEquals(2, productosAsus.size());
        Assertions.assertTrue(productosAsus.contains("Monitor 27 LED Full HD"));
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		var listProds = prodRepo.findAll();

        var mediaProductosAsus = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Asus"))
                .mapToDouble(p -> p.getPrecio())
                .average();

        System.out.println(mediaProductosAsus);

        Assertions.assertEquals(223.995, mediaProductosAsus.getAsDouble());
	}
	
	
	/** CORREGIDO ¿?
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		var listProds = prodRepo.findAll();

        var summaryStatistics = listProds.stream()
                .filter(p->p.getFabricante().getNombre().equals("Crucial"))
                .mapToDouble(p -> p.getPrecio())
                .summaryStatistics();

        System.out.println(summaryStatistics);

        double[] reduce = listProds.stream()
                .filter(p->p.getFabricante().getNombre().equals("Crucial"))
                .map(p->new double[]{p.getPrecio(),p.getPrecio(),p.getPrecio(),p.getPrecio(),0})
                .reduce(new double[]{Double.MAX_VALUE/*min*/, 0.0/*max*/, 0.0/*sum*/,0.0/*count*/},(a,b)->{
                    double minAct =0.0;
                    double maxAct =0.0;
                    double sumAct =0.0;
                    double countAct = 0.0;

                    double minAnt =  a[0];
                    if (b[0]< minAnt){
                        minAct = b[0];
                    } else {
                        minAct =minAnt;
                    }

                    double maxAnt =  a[1];
                    if (b[1]< maxAnt){
                        maxAct = b[1];
                    } else {
                        maxAct =minAnt;
                    }

                    double sumAnt =  a[2];
                    sumAct = sumAnt + b[2];

                    double countAnt =  a[3];
                    countAct = countAnt+1;

                    return new double[]{minAct, maxAct, sumAct, countAct};


                });

        //System.out.println(reduce);

	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
		var listFabs = fabRepo.findAll();
        var listProds = prodRepo.findAll();

        // --- Agrupar productos por fabricante ---
        var productosPorFabricante = listProds.stream()
                .collect(groupingBy(
                        p -> p.getFabricante().getNombre(),
                        counting()
                ));

        // --- Calcular también los que no tienen productos ---
        var totalPorFabricante = listFabs.stream()
                .collect(toMap(
                        f -> f.getNombre(),
                        f -> productosPorFabricante.getOrDefault(f.getNombre(), 0L)
                ));

        // --- Imprimir tabla formateada ---
        System.out.printf("%15s %15s%n", "Fabricante", "#Productos");
        System.out.println("-*".repeat(30));

        totalPorFabricante.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e ->
                        System.out.println(String.format("%15s %15d", e.getKey(), e.getValue()))
                );

        Assertions.assertEquals(2, totalPorFabricante.get("Asus"));
        Assertions.assertEquals(9, totalPorFabricante.size());
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
		var listFabs = fabRepo.findAll();


	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
		var listFabs = fabRepo.findAll();
        var listProds = prodRepo.findAll();

        //Sacamos todas las estadisticas de los Fabricantes
        var estadisticasPorFabricante = listProds.stream()
                .collect(groupingBy(
                        p -> p.getFabricante().getCodigo(),
                        summarizingDouble(p-> p.getPrecio())
                ));

        //Filtramos para que solo pasen las que tengan una media superior a 200
        var mediaMayor200 = estadisticasPorFabricante.entrySet().stream()
                .filter(e -> e.getValue().getAverage() > 200)
                .toList();

        //Sacamos por pantalla el Codigo de fabricante y las estadisticas de cada fabricante
        mediaMayor200.forEach(e -> System.out.println(
                "Código fabricante: " + e.getKey() + " → " + e.getValue()
                ));

        Assertions.assertEquals(3, mediaMayor200.size());
        Assertions.assertFalse(mediaMayor200.isEmpty(),
                "Debe existir al menos un fabricante con precio medio superior a 200€");

	}

	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		var listFabs = fabRepo.findAll();

        var fabricantesCon2oMas = listFabs.stream()
                .filter(f -> f.getProductos().size() >= 2)
                .map(f ->  f.getNombre())
                .sorted()
                .collect(toList());

        // --- Mostrar resultado ---
        fabricantesCon2oMas.forEach(s ->  System.out.println(s));

        Assertions.assertEquals(4, fabricantesCon2oMas.size());
        Assertions.assertTrue(fabricantesCon2oMas.contains("Crucial"));
	}

    /** CORREGIDO
     * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €.
     * Ordenado de mayor a menor número de productos.
     */
    @Test
    void test42() {
        var listFabs = fabRepo.findAll();

        var listadoNombre = listFabs.stream()
                .map(f -> new Object[]{
                        f.getNombre(), f.getProductos()
                        .stream()
                        .filter(p -> p.getPrecio() > 220)
                        .count()
                })
                .sorted(comparing((a) -> (Long) a[1], reverseOrder()))
                .toList();

        listadoNombre.forEach(s -> System.out.println("Fabricante " + s[0] + " Cantidad producto: " + s[1]));

        /**Correcion del profesor*/
        var MapNombre = listFabs.stream()
                .flatMap(fabricante -> fabricante.getProductos().stream())
                .collect(groupingBy(producto -> producto.getFabricante().getNombre()
                        , filtering(producto -> producto.getPrecio() > 220, counting())))
                .entrySet()
                .stream()
                .sorted(comparing((Map.Entry<String, Long> stringLongEntry) -> stringLongEntry.getValue(), reverseOrder()));


        MapNombre.forEach(s -> System.out.println(s));

        /** Mejora del test de Manolo*/
        record MiVector(String nomFab, long contProds) {
        }

        var listadoNombre3 = listFabs.stream()
                .map(f -> new MiVector(
                        f.getNombre(), f.getProductos()
                        .stream()
                        .filter(p -> p.getPrecio() > 220)
                        .count())
                )
                .sorted(comparing((a) -> a.contProds(), reverseOrder()))
                .toList();

        listadoNombre3.forEach(s-> System.out.println(s));

        Assertions.assertEquals(9, listadoNombre.size());
        //Assertions.assertTrue(listadoNombre.contains("Fabricante Crucial Cantidad producto: 1"));
    }
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		var listFabs = fabRepo.findAll();

        var fabsMasDe1000 = listFabs.stream()
                .filter(f -> f.getProductos() != null)
                .filter(f -> f.getProductos().stream()
                        .mapToDouble(p -> p.getPrecio())
                        .sum() > 1000)
                .map(f -> f.getNombre())
                .toList();

        fabsMasDe1000.forEach(s ->   System.out.println(s));

        Assertions.assertEquals(1, fabsMasDe1000.size());
        Assertions.assertTrue(fabsMasDe1000.contains("Lenovo"));
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		var listFabs = fabRepo.findAll();

        var fabsMasDe1000MenorAMayor = listFabs.stream()
                .filter(f -> f.getProductos().stream()
                        .mapToDouble(p -> p.getPrecio())
                        .sum() > 1000)
                .sorted(Comparator.comparingDouble(f -> f.getProductos().stream()
                        .mapToDouble(p  -> p.getPrecio())
                        .sum()))
                .map(f ->  f.getNombre())
                .toList();

        fabsMasDe1000MenorAMayor.forEach(s ->  System.out.println(s));

        Assertions.assertEquals(1, fabsMasDe1000MenorAMayor.size());
        Assertions.assertTrue(fabsMasDe1000MenorAMayor.contains("Lenovo"));
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		var listFabs = fabRepo.findAll();

        record productoMasCaro (String producto, double Precio, String fabricante){}
        var salida = listFabs.stream()
                .map(f-> {
                    var optionalProdMax = f.getProductos().stream()
                            .sorted(comparing(x -> x.getPrecio(), reverseOrder()))
                            .findFirst();
                    if (optionalProdMax.isPresent()) {
                        return optionalProdMax.get().getNombre() + " " + optionalProdMax.get().getPrecio() + " " + f.getNombre();
                    } else {
                        return f.getNombre() + " sin productos";
                    }

                })
                .collect(joining("\n"));

        System.out.println(salida);

        Assertions.assertTrue(salida.contains("GeForce GTX 1080 Xtreme 755.0 Crucial"));
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		var listFabs = fabRepo.findAll();

        double mediaDeTodosLosProductos = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream())
                .mapToDouble(p-> p.getPrecio()).average().orElse(0.0);

        double sumaTotal = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream())
                .map(p->p.getPrecio())
                .reduce(0.0,(a,b) -> a+b);

        double totalProds = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream()).count();

        double media2 = sumaTotal / totalProds;

        /*var listProdMayorMediaOrdenado = listFabs.stream()
                        .filter(f-> f.getProductos().get().getPrecio() => mediaDeTodosLosProductos)
                        .sorted()
                ;*/

        System.out.println(totalProds);
	}

    @Test
    void testReduce() {

        int sumaTotal = IntStream.iterate(1,i->i<100, i->i+2)
                .peek(value -> System.out.println(value))
                .reduce(0, (a,b) -> a+b);

        System.out.println(sumaTotal);
    }

    @Test
    void testJoining() {

        String hola = Stream.of("Hola", "mundo")
                .collect(joining(", ", ">", "!"));

        System.out.println(hola);

    }

}
