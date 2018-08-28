import java.awt.*;
import java.io.*;
import java.awt.event.*;

class MascaraGrafica extends Frame implements ComponentListener
{
  private final static int DEFAULT_SIZE = 500; 
	private Jogo j;
	private Desenhador drawer;
	private JanelaNovoJogo novo;
	private int tipojogo=2;
	private int tamTab=8;
	private double perExp=0.10;
	private int pecasIn=2;
	
	private Button ok;
	private CheckboxGroup c;
	private Checkbox _1on1;
	private Checkbox _1oncomp;
	private Choice tam;
	private Choice exp;
	private List init;
	
  public static void main (String args[])
  {
     MascaraGrafica mg = new MascaraGrafica();
  }
  
   MascaraGrafica()
   {
   	 Panel tipo=new Panel();
     tipo.setLayout(new GridLayout(2,1));
     Label ltipo=new Label("Seleccione o tipo de jogo: ");
     
     Panel tamanho=new Panel();
     tamanho.setLayout(new GridLayout(2,1));
     Label ltam=new Label("Tamanho do tabuleiro: ");
     
     Panel explosivas=new Panel();
     explosivas.setLayout(new GridLayout(2,1));
     Label lexp=new Label("Percentagem de peças explosivas: ");
     
     Panel pecasiniciais=new Panel();
     pecasiniciais.setLayout(new GridLayout(2,1));
     Label lpecas=new Label("Número de peças inicial no tabuleiro: ");
     
     Panel inicia=new Panel();
     ok=new Button("OK");
     
     //Tipo de jogo
     tipo.add(ltipo);
     c=new CheckboxGroup();
     Checkbox solo=new Checkbox("Modo Solo",c,true);
     tipo.add(solo);
     _1oncomp=new Checkbox("Contra o PC",c,false);
     tipo.add(_1oncomp);
     _1on1=new Checkbox("Contra um amigo",c,false);
     tipo.add(_1on1);
     
     //Tamanho do tabuleiro
     tamanho.add(ltam);
     tam=new Choice();
     tam.addItem("5x5");
     tam.addItem("6x6");
     tam.addItem("7x7");
     tam.addItem("8x8");
     tam.addItem("9x9");
     tamanho.add(tam);
     
     //Percentagem de peças explosivas
     explosivas.add(lexp);
     exp=new Choice();
     exp.addItem("5%");
     exp.addItem("10%");
     exp.addItem("20%");
     exp.addItem("30%");
     explosivas.add(exp);
     
     //Número de peças iniciais no tabuleiro
     pecasiniciais.add(lpecas);
     init=new List(4,false);
     init.addItem("2 peças");
     init.addItem("3 peças");
     init.addItem("4 peças");
     init.addItem("5 peças");
     pecasiniciais.add(init);
     
     //Botão OK
     inicia.add(ok);
     novo=new JanelaNovoJogo(c,tam,exp,init,_1on1,_1oncomp,ok);
     
     novo.setSize(350,350);
     novo.setBackground(Color.gray);
     novo.setLayout(new GridLayout(5,1));
     novo.add(tipo);
     novo.add(tamanho);
     novo.add(pecasiniciais);
     novo.add(explosivas);
     novo.add(inicia);
   	
     String [] arr = new String[4];
     arr[0] = "QUADS VER : 0.9";
     arr[1] = "Elaborado por :";     
     arr[2] = "José Luis Feiteirinha";          
     arr[3] = "Paulo Sacramento";     
		   	
		   	j=new Jogo(tipojogo,8,0.10,2);
		   	drawer=new Desenhador(8,this,j);
		   	add(drawer);
		   	
   	MenuBar menuBar = new MenuBar ();
   	
   	Menu menuJogo   = new Menu    ("Jogo");
   			MenuItem itemJogoNovo    = new MenuItem("Novo..."  );//Aparece janela a pedir opções
   			MenuItem itemJogoAnular  = new MenuItem("Anular"   );
   			MenuItem itemJogoSugestao= new MenuItem("Sugestão" );
   			MenuItem itemJogoAbrir   = new MenuItem("Abrir..." ); 
   			MenuItem itemJogoGravar  = new MenuItem("Gravar...");    			
   			MenuItem itemJogoSair    = new MenuItem("Sair"     );

			   	menuJogo.add(itemJogoNovo);
			   	menuJogo.addSeparator();
			   	menuJogo.add(itemJogoAnular);
			   	menuJogo.add(itemJogoSugestao);
			   	menuJogo.addSeparator();
			   	menuJogo.add(itemJogoAbrir);
   				menuJogo.add(itemJogoGravar);
			   	menuJogo.addSeparator();
			   	menuJogo.add(itemJogoSair);
			   	
		Menu menuAjuda = new Menu("Ajuda");
			MenuItem itemAjudaIndice=new MenuItem("Índice...");
			MenuItem itemAjudaSobre=new MenuItem("Sobre...");
			
			menuAjuda.add(itemAjudaIndice);
			menuAjuda.addSeparator();
			menuAjuda.add(itemAjudaSobre);
			   	
   	menuBar.add(menuJogo);
   	menuBar.add(menuAjuda);
   	
   	setMenuBar(menuBar);

        addComponentListener(this);
		 		 
     MenuChanger mc = new MenuChanger(this,arr);
     mc.start();

   } 
   
   // INTERFACES E COMPANHIA
   
   public boolean action(Event event, Object arg)
	 {
	 	FileDialog fd;
	 	
	 	if(event.target instanceof MenuItem)
	 	{
	 		if(arg.equals("Novo..."))
	 		{
	 			j=null;
	 			remove(drawer);
	 			novo.show();
	 		/*
	 					j=novo.getJogo();
		 				drawer = new Desenhador(DEFAULT_SIZE,this,j);
	 			
   	 			add(drawer);
   	 			drawer.setSize(DEFAULT_SIZE,DEFAULT_SIZE);	 			
	 				drawer.repaint();*/
	 		}
	 			
	 		if(arg.equals("Anular"))
	 		{
	 			j.undo();
	 		}	
	 		//if(arg.equals("Sugestão"))
	 			
	 		if(arg.equals("Gravar..."))
	 		{	 			
	 			JogoIO ioSave=new JogoIO(this.j);	 			
	 			fd=new FileDialog(this,"Gravar Jogo");
	 			fd.show();
	 			ioSave.save(fd.getFile());
	 		}
	 			
	 		if(arg.equals("Abrir..."))
	 		{
	 			JogoIO ioLoad=new JogoIO();

				int w =getSize().width;
				int h =getSize().height;				
				
				j = null;
				remove(drawer);
				
	 			fd=new FileDialog(this,"Abrir Jogo");
	 			fd.show();
	 			j=ioLoad.load(fd.getFile());
	 			drawer=new Desenhador(DEFAULT_SIZE,this,this.j);
	 			
	 			add(drawer);
	 				 			
	 			drawer.setSize(DEFAULT_SIZE,DEFAULT_SIZE);	 			
	 			drawer.repaint();	 			
	 			
	 		}
	 		if(arg.equals("Sair"))
	 		{
	 			System.exit(0);
	 		}
	 		//if(arg.equals("Índice..."))
	 			
	 		//if(arg.equals("Sobre..."))
	 	}
	 	repaint();
	 	
	 	return true;
	 }
	 
   public void componentMoved   (ComponentEvent e){}
   public void componentHidden  (ComponentEvent e){}   
   public void componentShown   (ComponentEvent e){}      
   
   public void componentResized (ComponentEvent e)
     {
         int x =getSize().width; 
         int y =getSize().height;     
         drawer.setSize(x,y);
     }      
     
  
  
}// fim da class