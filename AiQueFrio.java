import java.io.IOException;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AiQueFrio {

    private static Random aleatorio = new Random();

    public static void main(String[] args) throws IOException {
        
        LinkedList<ElementoX> grafo = ProcessaDados();  
        FileWriter file1 = new FileWriter("Segundo_Infectados.txt");
        FileWriter file2 = new FileWriter("Segundo_Suscetiveis.txt");
        FileWriter file3 = new FileWriter("Segundo_Removidos.txt");
        PrintWriter saida1 = new PrintWriter(file1);
        PrintWriter saida2 = new PrintWriter(file2);
        PrintWriter saida3 = new PrintWriter(file3);

        int inf = aleatorio.nextInt(grafo.size());
        LinkedList<Integer> infectados = new LinkedList<>();   
        infectados.add(inf);
        grafo.get(inf).estado = 'I';
        ElementoX.Infectados++;
        ElementoX.Suscetiveis--;

        int m = 0;
        while (!infectados.isEmpty()){

            int atual = infectados.getFirst();
            Busca(grafo, atual, infectados);
            if(grafo.get(atual).estado != 'I') infectados.removeFirst();

            saida1.println(ElementoX.Infectados);
            saida2.println(ElementoX.Suscetiveis);
            saida3.println(ElementoX.Removidos);

            m++;
            System.out.println(m);
        }
        
        saida1.close();
        saida2.close();
        saida3.close();
    }

    private static void Busca(LinkedList<ElementoX> grafo, int vertice, LinkedList<Integer> inf) {

        final double c = 0.5;
        final double r = 0.5;

        double x = aleatorio.nextDouble();
            
        if (x <= r){

            grafo.get(vertice).estado = 'R';
            ElementoX.Removidos++;
            ElementoX.Infectados--;    
        } 
        
        else {
            
            for (int b : grafo.get(vertice).Filhos){

                if (grafo.get(b).estado == 'S'){

                    double y = aleatorio.nextDouble();

                    if (y <= c){

                        grafo.get(b).estado = 'I';
                        ElementoX.Infectados++;
                        ElementoX.Suscetiveis--;
                        inf.add(b);
                    } 
                }
            }
        }
    }


    private static LinkedList<ElementoX> ProcessaDados() throws IOException {

        String caminho = "traducao1.txt"; 

        File graph = new File(caminho);
        Scanner sc = new Scanner(graph);
                
        LinkedList<ElementoX>nosss = new LinkedList<ElementoX>();
        
        for(int i = 0; i < 61839; i++){
            nosss.add(new ElementoX(i,'S'));
            ElementoX.Suscetiveis++;
        }    

        sc.nextLine();
        sc.nextLine();
        int m = 0;

        while(sc.hasNext()){

            m++;
            System.out.println(m);

            String linha = sc.nextLine();
            String [] vertice = linha.split(" ");
            
            int no = Integer.parseInt(vertice[1]);
            
            nosss.get(no).Filhos.add(Integer.parseInt(vertice[0]));
        }

        sc.close();
    
        return nosss;   
    }   
}

class ElementoX {

    public static int Suscetiveis = 0;
    public static int Infectados = 0;
    public static int Removidos = 0;
    public int ID;
    public char estado;
    public LinkedList<Integer> Filhos;

    public ElementoX (int ID, char estado){

        Filhos = new LinkedList<>();
        this.ID = ID;
        this.estado = estado;
    }
}
