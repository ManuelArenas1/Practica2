import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
public class Main {
    public static void ventasNYC(List<Ventas> ventasList){
    ventasList.stream()
            .filter(l -> l.getCity().equals("NYC"))
            .forEach(l -> {
                System.out.print("orderNumber: " + l.getOrderNumber() + ", ");
                System.out.print("quantityOrdered: " + l.getQuantityOrdered() + ", ");
                System.out.print("priceEach: " + l.getPriceEach() + ", ");
                System.out.print("orderLineNumber: " + l.getOrderLineNumber() + ", ");
                System.out.print("sales: " + l.getSales() + ", ");
                System.out.print("orderDate: " + l.getOrderDate() + ", ");
                System.out.print("status: " + l.getStatus() + ", ");
                System.out.print("qtr_id: " + l.getQtrId() + ", ");
                System.out.print("month_id: " + l.getMonthId() + ", ");
                System.out.print("year_id: " + l.getYearId() + ", ");
                System.out.print("productLine: " + l.getProductLine() + ", ");
                System.out.print("msrp: " + l.getMsrp() + ", ");
                System.out.print("productCode: " + l.getProductCode() + ", ");
                System.out.print("customerName: " + l.getCustomerName() + ", ");
                System.out.print("phone: " + l.getPhone() + ", ");
                System.out.print("adressLine1: " + l.getAddressLine1() + ", ");
                System.out.print("adressLine2: " + l.getAddressLine2() + ", ");
                System.out.print("city: " + l.getCity() + ", ");
                System.out.print("state: " + l.getState() + ", ");
                System.out.print("postalCode: " + l.getPostalCode() + ", ");
                System.out.print("country: " + l.getCountry() + ", ");
                System.out.print("territory: " + l.getTerritory() + ", ");
                System.out.print("contactLastName: " + l.getContactLastName() + ", ");
                System.out.print("contactFirstName: " + l.getContactFirstName() + ", ");
                System.out.println("dealSize: " + l.getDealSize());
            });
    }

    public static double ventasNewYork(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(l -> {
                    return l.getCity().equals(city);
                })
                .mapToDouble(l -> l.getSales())
                .sum();
    }

    public static int carrosClasicosNYC(List<Ventas> ventasList, String city) {
        return ventasList.stream()
                .filter(l -> {
                    return l.getCity().equals(city) && l.getProductLine().equals("Classic Cars");
                })
                .mapToInt(l -> l.getQuantityOrdered())
                .sum();
    }
    public static double ventasCarrosClasicosNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(l-> {
                    return l.getCity().equals(city) && l.getProductLine().equals("Classic Cars");
                })
                .mapToDouble(l-> l.getSales())
                .sum();

    }
    public static int motocicletasNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(l -> {
                    return l.getCity().equals(city) && l.getProductLine().equals("Motorcycles");
                })
                .mapToInt(l -> l.getQuantityOrdered())
                .sum();
    }

    public static double ventasMotocicletasNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(l -> {
                    return l.getCity().equals(city) && l.getProductLine().equals("Motorcycles");
                })
                .mapToDouble(l -> l.getSales())
                .sum();
    }



    public static String clienteMasAutosComproEnNY(List<Ventas> ventasList) {
        Map<String, Integer> cantidadAutosPorCliente = new HashMap<>();
        ventasList.stream()
                .filter(l -> l.getCity().equals("NYC") && (l.getProductLine().equals("Classic Cars") || l.getProductLine().equals("Vintage Cars")))
                .forEach(l -> {
                    String cliente = l.getCustomerName();
                    Integer cantidadAutos = l.getQuantityOrdered();
                    cantidadAutosPorCliente.put(cliente, cantidadAutosPorCliente.getOrDefault(cliente, 0) + cantidadAutos);
                });
        return cantidadAutosPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public static String clienteMasCompro(List<Ventas> ventasList) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventasList.forEach(l -> {
            String cliente = l.getCustomerName();
            Integer cantidad = l.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay cliente que compró más");
    }

    public static String clienteMenosCompro(List<Ventas> ventasList) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventasList.forEach(l -> {
            String cliente = l.getCustomerName();
            Integer cantidad = l.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay un cliente que compró menos");
    }




    public static List<Ventas> readVentasFromFile(String fileName) throws IOException {
        List<Ventas> ventasList = new ArrayList<>();

        Pattern pattern = Pattern.compile("^(\\d+),\"(\\d+)\",\"(\\d+\\.\\d+)\",(\\d+),(\\d+\\.\\d+),\"([\\d/]+)\",\"(\\w+)\",(\\d+),(\\d+),(\\d+),\"(.+)\",(\\d+),\"(\\w+)\",\"(.+)\",\"(.+)\",\"(.+)\",\"(.+)\",\"(.+)\",(\\w+),(\\w+),(\\d+),\"(.+)\",\"(.+)\",\"(.+)\",(\\d+\\.\\d+)\",(\\w+)\"(.+)\",\"(.+)\",\"(\\w+)\"$");


        //se abre el archivo csv y se guardan los datos
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            ventasList = br.lines()
                    .skip(1)
                    .map(line -> {
                        Ventas ventas = new Ventas();
                        String[] posiciones = pattern.matcher(line).replaceAll("$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$21,$22,$23,$24,$25").split(",");
                        ventas.setOrderNumber(Integer.parseInt(posiciones[0]));
                        ventas.setQuantityOrdered(Integer.parseInt(posiciones[1]));
                        ventas.setPriceEach(Double.parseDouble(posiciones[2]));
                        ventas.setOrderLineNumber(Integer.parseInt(posiciones[3]));
                        ventas.setSales(Double.parseDouble(posiciones[4]));
                        ventas.setOrderDate(posiciones[5]);
                        ventas.setStatus(posiciones[6]);
                        ventas.setQtrId(Integer.parseInt(posiciones[7]));
                        ventas.setMonthId(Integer.parseInt(posiciones[8]));
                        ventas.setYearId(Integer.parseInt(posiciones[9]));
                        ventas.setProductLine(posiciones[10]);
                        ventas.setMsrp(Integer.parseInt(posiciones[11]));
                        ventas.setProductCode(posiciones[12]);
                        ventas.setCustomerName(posiciones[13]);
                        ventas.setPhone(posiciones[14]);
                        ventas.setAddressLine1(posiciones[15]);
                        ventas.setAddressLine2(posiciones[16]);
                        ventas.setCity(posiciones[17]);
                        ventas.setState(posiciones[18]);
                        ventas.setPostalCode(posiciones[19]);
                        ventas.setCountry(posiciones[20]);
                        ventas.setTerritory(posiciones[21]);
                        ventas.setContactLastName(posiciones[22]);
                        ventas.setContactFirstName(posiciones[23]);
                        ventas.setDealSize(posiciones[24]);
                        return ventas;
                    })
                    .toList();
        }

        return ventasList;
    }


    public static void main(String[] args) throws IOException {
        List<Ventas> ventasList = Main.readVentasFromFile("C:\\Users\\Asus\\IdeaProjects\\Practica2\\src\\sales_data.csv");      //Esta linea busca el archivo .csv

        Main.ventasNYC(ventasList);
        System.out.println("El total de ventas de New York es de: " + Main.ventasNewYork(ventasList, "NYC"));
        System.out.println("New York vendió " + Main.carrosClasicosNYC(ventasList, "NYC") + " autos clásicos");
        System.out.println("El total de ventas de autos clásicos en New York es de : " + Main.ventasCarrosClasicosNYC(ventasList, "NYC"));
        System.out.println("New York vendió " + Main.motocicletasNYC(ventasList, "NYC") + " motocicletas");
        System.out.println("El total de ventas de motocicletas en New York es de : " + Main.ventasMotocicletasNYC(ventasList, "NYC"));
        System.out.println("El cliente que más autos compró en New York es: " + Main.clienteMasAutosComproEnNY(ventasList));
        System.out.println("El cliente que más compró es: " + Main.clienteMasCompro(ventasList));
        System.out.println("El cliente que menos compró es: " + Main.clienteMenosCompro(ventasList));

    }
}