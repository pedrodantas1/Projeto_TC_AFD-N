package controller.operation;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class InterseccaoAFD {
    String nome1, nome2, nome3,transition, inicialAutomato, finalAutomato;
    String finalTransicoes,openTransition,closeTransition,structure;
    String from1,from2,to1,to2,read1,read2,dado,dadoAux,concatenarEstado;
    String inicio, fim,state,stateName,stateNameEnd,auxConcatenarEstado;
    char c,readA,readB;
    int contID,fromA,fromB,toA,toB,contadorEstadoB;
    FileWriter novoAutomato;
    File objeto1,objeto2;

    public InterseccaoAFD(String nome1, String nome2, String nome3) throws IOException
    {
        this.state = "		<state id=\"";
        this.stateName = "\" name=\"";
        this.stateNameEnd = "\">&#13;\n";
        this.from1 = "			<from>";
        this.from2 = "</from>&#13;";
        this.to1 = "			<to>";
        this.to2 = "</to>&#13;";
        this.read1 = "			<read>";
        this.read2 = "</read>&#13;";
        this.readA = ' ';
        this.readB = ' ';
        this.structure = "</structure>";
        this.inicio = "";
        this.fim = "";
        this.dadoAux = "";
        this.contadorEstadoB = 0;
        this.contID = 0;
        this.concatenarEstado = "";
        this.nome1 = nome1;
        this.nome2 = nome2;
        this.nome3 = nome3;
        this.novoAutomato = new FileWriter(nome3);
        this.objeto1 = new File(nome1);
        this.objeto2 = new File(nome2);
        this.transition = "		<!--The list of transitions.-->&#13;";
        this.finalAutomato = "			<final/>&#13;";
        this.inicialAutomato = "			<initial/>&#13;";
        this.finalTransicoes = "	</automaton>&#13;";
        this.openTransition = "		<transition>&#13;";
        this.closeTransition = "		</transition>&#13;";
    }

    public void juntarAFD() throws IOException
    {
        Scanner scan = new Scanner(System.in);
        contadorDeEstadosB(scan, objeto2);
        Scanner leitura1 = new Scanner(objeto1);
        for (int i = 0; i < 4; i++) {
            dado = leitura1.nextLine();
            novoAutomato.write(dado+"\n");
        }dado = leitura1.nextLine();

        while(!dadoAux.equals(transition))
        {
            getNome(dado);
            for (int i = 0; i < 3; i++) {
                dado = leitura1.nextLine();
            }
            inicioFimBase(leitura1);
            inicioFimBase(leitura1);
            dadoAux = leitura1.nextLine();
            Scanner leitura2 = new Scanner(objeto2);
            for (int i = 0; i < 5; i++) {
                dado = leitura2.nextLine();
            }
            auxConcatenarEstado = concatenarEstado;
            while(!dado.equals(transition))
            {
                getNome(dado);
                novoAutomato.write(state+(contID++)+stateName+concatenarEstado+stateNameEnd);
                for (int i = 0; i < 3; i++) {
                    dado = leitura2.nextLine();
                }
                inicioFim(leitura2);
                inicioFim(leitura2);
                novoAutomato.write(dado+"\n");
                dado = leitura2.nextLine();
                concatenarEstado = auxConcatenarEstado;
            }dado = dadoAux;
            leitura2.close();
            concatenarEstado = "";
            inicio = "";
            fim = "";
        }
        novoAutomato.write(dado+"\n");
        dadoAux = leitura1.nextLine();
        while(dadoAux.equals(openTransition))
        {
            dado = leitura1.nextLine();
            fromA = getIndiceTransicaoFromTo(dado);
            dado = leitura1.nextLine();
            toA = getIndiceTransicaoFromTo(dado);
            dado = leitura1.nextLine();
            readA = getIndiceTransicaoRead(dado);
            dado = leitura1.nextLine();
            dadoAux = leitura1.nextLine();
            Scanner leitura2 = new Scanner(objeto2);
            dado = leitura2.nextLine();
            while(!dado.equals(openTransition))
            {
                dado = leitura2.nextLine();
            }
            while(dado.equals(openTransition))
            {
                dado = leitura2.nextLine();
                fromB = getIndiceTransicaoFromTo(dado);
                dado = leitura2.nextLine();
                toB = getIndiceTransicaoFromTo(dado);
                dado = leitura2.nextLine();
                readB = getIndiceTransicaoRead(dado);
                dado = leitura2.nextLine();
                dado = leitura2.nextLine();
                if(readA == readB)
                {
                    int de = (contadorEstadoB*fromA) + fromB;
                    int para = (contadorEstadoB*toA) + toB;
                    novoAutomato.write(openTransition+"\n");
                    novoAutomato.write(from1+de+from2+"\n");
                    novoAutomato.write(to1+para+to2+"\n");
                    novoAutomato.write(read1+readA+read2+"\n");
                    novoAutomato.write(closeTransition+"\n");
                }
            }
            leitura2.close();
        }
        novoAutomato.write(finalTransicoes+"\n"+structure);
        novoAutomato.close();
        scan.close();
        leitura1.close();
    }

    public char getIndiceTransicaoRead(String dado)
    {
        int i;
        for ( i = 0; dado.charAt(i) != '>'; i++);
        i++;
        return dado.charAt(i);
    }

    public void contadorDeEstadosB(Scanner scan, File objeto) throws FileNotFoundException
    {
        Scanner leitura = new Scanner(objeto);
        String dado="";
        for (int i = 0; i < 5; i++) {
            dado = leitura.nextLine();
        }
        while(!dado.equals(transition))
        {
            for (int i = 0; i < 3; i++) {
                dado = leitura.nextLine();
            }
            if(dado.equals(inicialAutomato) || dado.equals(finalAutomato))
            {
                dado = leitura.nextLine();
            }
            if(dado.equals(inicialAutomato) || dado.equals(finalAutomato))
            {
                dado = leitura.nextLine();
            }
            dado = leitura.nextLine();
            this.contadorEstadoB++;
        }leitura.close();
    }

    public int getIndiceTransicaoFromTo(String dado)
    {
        int i;
        for (i=0;dado.charAt(i) != '>';i++);
        i++;
        int getNumero = Character.getNumericValue(dado.charAt(i));
        i++;
        while(Character.isDigit(dado.charAt(i)))
        {
            getNumero*=10;
            getNumero+=Character.getNumericValue(i);
            i++;
        }
        return getNumero;
    }

    public void inicioFim(Scanner leitura2) throws IOException
    {
        if(dado.equals(inicialAutomato) || dado.equals(finalAutomato))
            {
                if(dado.equals(inicio))
                {
                novoAutomato.write(dado+"\n");
                }
                if(dado.equals(fim))
                {
                novoAutomato.write(dado+"\n");
                }
                dado = leitura2.nextLine();
            }
    }

    public void inicioFimBase(Scanner leitura1)
    {
        if(dado.equals(inicialAutomato) || dado.equals(finalAutomato))
            {
                if(dado.equals(inicialAutomato))
                {
                    inicio = dado;
                }
                if(dado.equals(finalAutomato))
                {
                    fim = dado;
                }
                dado = leitura1.nextLine();
            }
    }

    public void getNome(String dado)
    {
        int i=0;
        for(int aux=0;aux<3;i++)
            if(dado.charAt(i) == '\"')
                aux++;
        while(dado.charAt(i) != '\"')
        {
            concatenarEstado = concatenarEstado + dado.charAt(i);
            i++;
        }
    }
}
