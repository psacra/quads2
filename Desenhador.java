import java.awt.*;
import java.awt.event.*;

public class Desenhador extends Canvas implements MouseListener
{
	private final static int menuSizeY = 50;
	private int size;    //tamanho da Frame
	private int quadTam;  // tamanho dos Quads do Tabuleiro
	private int quadCPTam;
	private final static int nQuadsColunaCaixa=14;
	private final static int nQuadsLinhaCaixa=6;	
	private final static Color LINE_COLORS = Color.black;
  private final static Color COR_FUNDO   = Color.gray;
	private final static int supX = 1 ;
	private final static int supY = 0;	
	private final static int RAIO_EXPLOSIVAS=5;
	
	private static int supCPX;
	private static int supCPY;//coordenadas superiores da CaixaPeças
		
	private static int selectedTam;
	private static int selectedSupX;
	private static int selectedSupY;	

	private Tabuleiro tab;
	private Image offGraphics;
  private Frame f;
	private Jogo j;
	
	// TIPOS DE PADRÕES
	private final static int PADRAO_LINHAS_HORIZONTAIS= 0 ;
	private final static int PADRAO_LINHAS_VERTICAIS  = 1 ;	
	private final static int PADRAO_XADRES = 2 ;	
	private final static int PADRAO_CINZA = 3 ;		
	private final static int PADRAO_PRETO = 4 ;			

  private final static int DIST_LINHAS = 4;	
	
	//divide a frame
	/*
	___________________
	|________E_________|
	|             |    |
	|             |    |
	|    A        | B  |
	|             |    |
	|_____________|____|
	|    C        | D  |	
	|_____________|____|
                      	
	YY = sizeY-menuY // espaço do menu
	
	A - tem como tamanho 70% * sizeX , 70% * YY
	B - tem como tamanho 30% * sizeX , 70% * YY	
	C - tem como tamanho 70% * sizeX , 30% * YY
	D - tem como tamanho 30% * sizeX , 30% * YY	
	
	A = Tabuleiro        =~ tab
	B = Info             =~ info
	C = Caixa Quads      =~ cQuads
	D = Quad Selecionado =~ qSel
	
	E - sizeX , menuY
	
	*/
	
  private static final double tabXPercent    = 0.70;
  private static final double tabYPercent    = 0.70;  //parte A
  
  private static final double infoXPercent   = 0.30;
  private static final double infoYPercent   = 0.70;  //parte B
	
  private static final double cQuadsXPercent = 0.70;
  private static final double cQuadsYPercent = 0.30;  //parte C
  
  private static final double qSelXPercent   = 0.30;
  private static final double qSelYPercent   = 0.30;  //parte D


	
	public Desenhador(int size,Frame f,Tabuleiro tab)
	{
		addMouseListener(this);
    this.size = size;
    this.tab  = tab ;
    this.f=f;
			
		f.resize(size,size+menuSizeY);
		f.show();
    resize(size,size);
    setBackground(COR_FUNDO);    
    
		quadTam   = (int)((size*tabXPercent)/tab.getN());
		quadCPTam = (int)(size*cQuadsYPercent)/nQuadsLinhaCaixa;
		supCPX = 1;
		supCPY =(int)((size*tabYPercent)+supY);
		
		selectedTam  = (int)(size*qSelXPercent);
		selectedSupY = (int)(size-size*qSelYPercent)+supY;
		selectedSupX = (int)(size-size*qSelXPercent);
	}
	
	public Desenhador(int size,Frame f,Jogo j)
	{
		addMouseListener(this);
    this.f=f;    
    this.size = size;
    this.j=j;
    
    this.tab  = j.getTabuleiro();
    
 		f.resize(size,size+menuSizeY);
		f.show();
    resize(size,size+menuSizeY);
    setBackground(COR_FUNDO);    
		quadTam   = (int)((size*tabXPercent)/tab.getN());
		quadCPTam = (int)(size*cQuadsYPercent)/nQuadsLinhaCaixa;
		supCPX = 1;
		supCPY =(int)((size*tabYPercent)+supY);
		
		selectedTam  = (int)(size*qSelXPercent);
		selectedSupY = (int)(size-size*qSelYPercent)+supY;
		selectedSupX = (int)(size-size*qSelXPercent);
		
		
		
	}

  public void setSize(int x,int y)
  {   
    if (x>y-menuSizeY) size = y-menuSizeY;
    else size=x;
    if (size<400)size = 400;

    f.resize(size,size+menuSizeY);
    resize(size,size);

    quadTam   = ((int)(size*tabXPercent))/tab.getN();
    quadCPTam = ((int)(size*cQuadsYPercent))/nQuadsLinhaCaixa;
    supCPX = 1;
    supCPY =(int)((size*tabYPercent)+supY);
    
    selectedTam  = (int)(size*qSelXPercent);
    selectedSupY = (int)(size-size*qSelYPercent)+supY;
    selectedSupX = (int)(size-size*qSelXPercent);
   	
   	offGraphics=createImage(getSize().width,getSize().height);     	     	
    
  }

  	
  public void drawUp(Graphics g,int tam,int x,int y,int padrao)
	{
		Polygon p;
    g.setColor(LINE_COLORS);
		switch (padrao)
		{
						//# ************    centroY = y+tam/2  ; centroX = x+tam/2			
			case (PADRAO_LINHAS_HORIZONTAIS):
				    for (int cnty = y ; cnty <y+tam/2;cnty++)
							if (cnty%DIST_LINHAS==0) 
									g.drawLine(x+(cnty-y),cnty,x+tam-(cnty-y),cnty);
			break;
				
			case (PADRAO_LINHAS_VERTICAIS):
				    for (int cntx = x ; cntx <=x+tam;cntx++)
							if (cntx%DIST_LINHAS==0)
										if (cntx<=x+tam/2)
												g.drawLine(cntx,y,cntx,y+(cntx-x));
										else
												g.drawLine(cntx,y,cntx,(y+tam-(cntx-x)));									
			break;
			
			case (PADRAO_XADRES):
						drawUp(g,tam,x,y,PADRAO_LINHAS_HORIZONTAIS);
						drawUp(g,tam,x,y,PADRAO_LINHAS_VERTICAIS);						
			break;
	
			case (PADRAO_CINZA):
					p = new Polygon();
					p.addPoint(x,y);
					p.addPoint(x+tam,y);
					p.addPoint(x+tam/2,y+tam/2);										
				  g.setColor(Color.lightGray);
				  g.fillPolygon(p);
			break;
				
			case (PADRAO_PRETO):
					p = new Polygon();
					p.addPoint(x,y);
					p.addPoint(x+tam,y);
					p.addPoint(x+tam/2,y+tam/2);										
				  g.fillPolygon(p);
				break;	
		}//fim switch
		
	} //fim do drawUP

  public void drawRight(Graphics g,int tam,int x,int y,int padrao)
	{
		Polygon p;
    g.setColor(LINE_COLORS);
   switch (padrao)
		{
				//# ************    centroY = y+tam/2  ; centroX = x+tam/2			
        
			case (PADRAO_LINHAS_HORIZONTAIS):
				    for (int cntx = x+tam/2 ; cntx <x+tam;cntx++)
              if (cntx%DIST_LINHAS==0) 
                  g.drawLine(cntx,y+tam/2-(cntx-(x+tam/2)),cntx,y+tam/2+(cntx-(x+tam/2)));
  	
			break;
			
			case (PADRAO_LINHAS_VERTICAIS):
				for (int cnty = y ; cnty <=y+tam;cnty++)
              if (cnty%DIST_LINHAS==0)
                    if (cnty<=y+tam/2)                      
                        g.drawLine(x+tam-(cnty-y),cnty,x+tam,cnty);
                    else
                        g.drawLine(x+(cnty-y),cnty,x+tam,cnty);
       break;
			
			case (PADRAO_XADRES):
						drawRight(g,tam,x,y,PADRAO_LINHAS_HORIZONTAIS);
						drawRight(g,tam,x,y,PADRAO_LINHAS_VERTICAIS);						
			break;
	
			case (PADRAO_CINZA):
					p = new Polygon();
					p.addPoint(x+tam,y);
					p.addPoint(x+tam,y+tam);
					p.addPoint(x+tam/2,y+tam/2);										
				  g.setColor(Color.lightGray);
				  g.fillPolygon(p);
			break;
				
			case (PADRAO_PRETO):
					p = new Polygon();
					p.addPoint(x+tam,y);
					p.addPoint(x+tam,y+tam);
					p.addPoint(x+tam/2,y+tam/2);										
				  g.fillPolygon(p);
				break;	
		}//fim switch
		
	} //fim do drawRight




  public void drawDown(Graphics g,int tam,int x,int y,int padrao)
  {
    Polygon p;
    g.setColor(LINE_COLORS);   
    switch (padrao)
    {

            //    centroY = y+tam/2  ; centroX = x+tam/2      
                        
      case (PADRAO_LINHAS_HORIZONTAIS):
            for (int cnty = y+tam/2 ; cnty <y+tam;cnty++)
              if (cnty%DIST_LINHAS==0) 
                  g.drawLine(x + tam - (cnty-y),cnty,x+(cnty-y),cnty);
      break;
        
      case (PADRAO_LINHAS_VERTICAIS):
            for (int cntx = x; cntx <=x+tam;cntx++)
              if (cntx%DIST_LINHAS==0)
                    if (cntx <= x+tam/2)
                        g.drawLine(cntx,y+tam-(cntx-x),cntx,y+tam);
                    else
                        g.drawLine(cntx,y+(cntx-x),cntx,y+tam);                  
      break;
      
      case (PADRAO_XADRES):
            drawDown(g,tam,x,y,PADRAO_LINHAS_HORIZONTAIS);
            drawDown(g,tam,x,y,PADRAO_LINHAS_VERTICAIS);            
      break;
  
      case (PADRAO_CINZA):
          p = new Polygon();
          p.addPoint(x,y+tam);
          p.addPoint(x+tam,y+tam);
          p.addPoint(x+tam/2,y+tam/2);                    
          g.setColor(Color.lightGray);
          g.fillPolygon(p);
      break;
        
      case (PADRAO_PRETO):
          p = new Polygon();
          p.addPoint(x,y+tam);
          p.addPoint(x+tam,y+tam);
          p.addPoint(x+tam/2,y+tam/2);                    
          g.fillPolygon(p);
        break;  
    }//fim switch
  
  } //fim do drawDown


  public void drawLeft(Graphics g,int tam,int x,int y,int padrao)
  {
    Polygon p;
    g.setColor(LINE_COLORS);

    switch (padrao)
    {
            // #  ********    centroY = y+tam/2  ; centroX = x+tam/2      
      case (PADRAO_LINHAS_HORIZONTAIS):
       for (int cntx = x ; cntx <x+tam/2;cntx++)
              if (cntx%DIST_LINHAS==0) 
                  g.drawLine(cntx,y+(cntx-x),cntx,y+tam-(cntx-x));
       break;
      
      case (PADRAO_LINHAS_VERTICAIS):
           for (int cnty = y ; cnty <=y+tam;cnty++)
              if (cnty%DIST_LINHAS==0)
                    if (cnty<=y+tam/2)                      
                        g.drawLine(x,cnty,x+(cnty-y),cnty);
                    else
                        g.drawLine(x,cnty,x+tam-(cnty-y),cnty);
           
       break;
      
      case (PADRAO_XADRES):
            drawLeft(g,tam,x,y,PADRAO_LINHAS_HORIZONTAIS);
            drawLeft(g,tam,x,y,PADRAO_LINHAS_VERTICAIS);            
      break;
  
      case (PADRAO_CINZA):
          p = new Polygon();
          p.addPoint(x,y);
          p.addPoint(x,y+tam);
          p.addPoint(x+tam/2,y+tam/2);                    
          g.setColor(Color.lightGray);
          g.fillPolygon(p);
      break;
        
      case (PADRAO_PRETO):
          p = new Polygon();
          p.addPoint(x,y);
          p.addPoint(x,y+tam);
          p.addPoint(x+tam/2,y+tam/2);                    
          g.fillPolygon(p);
        break;  
    }//fim switch
    
  } //fim do drawLeft
  	
	private void drawQuad(Graphics g,int tam,int x,int y,Quad q)// x,y coordenadas do canto superior esquerdo
	{		
    g.clearRect(x,y,tam,tam);//limpa o Quad por dentro            
		if(!q.isEmpty())
		{
			drawUp(g,tam,x,y,q.getUp());
			drawRight(g,tam,x,y,q.getRight());			
      drawDown(g,tam,x,y,q.getDown());      
      drawLeft(g,tam,x,y,q.getLeft());          
		}
		if(q.isExplosiva())
		{
			g.setColor(Color.red);
			g.fillOval((x+tam/2)-(tam/RAIO_EXPLOSIVAS),(y+tam/2)-(tam/RAIO_EXPLOSIVAS),2*(tam/RAIO_EXPLOSIVAS),2*(tam/RAIO_EXPLOSIVAS));
		}
    g.setColor(Color.white);
    g.drawRect(x,y,tam,tam);
	}
	
 	public void showTab(Graphics g)
	{	
		this.tab  = j.getTabuleiro();
		repaint();
		
		for(int cntX =0; cntX < tab.getN();cntX++)
			for(int cntY=0; cntY < tab.getN();cntY++)
				drawQuad(g,quadTam,supX+cntX*quadTam,supY+cntY*quadTam,tab.getQuad(cntX,cntY));
	}
	
	public void showCaixa(Graphics g)
	{
		  int nTotal = tab.getCaixa().getN();

			int cntX=0;
			g.clearRect(supCPX,supCPY,quadCPTam*nQuadsColunaCaixa,quadCPTam*nQuadsLinhaCaixa);
			
  		for (int cntY=0;(cntY<nQuadsLinhaCaixa);cntY++)				
	    		for (     cntX=0;(cntX<nQuadsColunaCaixa) && ((cntX+cntY*nQuadsColunaCaixa) < nTotal);cntX++)			
						{
							drawQuad(g,quadCPTam,supCPX+cntX*quadCPTam,supCPY+cntY*quadCPTam,tab.getCaixa().getQuad(cntX+cntY*nQuadsColunaCaixa));
              if (tab.getCaixa().isSelected(cntX+cntY*nQuadsColunaCaixa))
              {
                g.setColor(Color.green);
                g.fillRect(supCPX+cntX*quadCPTam,supCPY+cntY*quadCPTam,quadCPTam,quadCPTam);
              }
              
						}
	}// fim do showCaixa
		
		
	public void showSelected(Graphics g)
	{
			drawQuad(g,selectedTam,selectedSupX,selectedSupY,tab.getCaixa().getSelected());
	}
	
  public void paint(Graphics g)
   {
   	 if(offGraphics==null)
 	 	 {
   	 	offGraphics=createImage(getSize().width,getSize().height);
   	 }
   	 Graphics go= offGraphics.getGraphics();
  	 
 		 paintFrame(go);
   	 
   	 g.drawImage(offGraphics,0,0,null);
   	 
   }
   
   
  public void update(Graphics g) {
      paint(g);
  }
	
	
  public void paintFrame(Graphics g)
  {
    showTab(g);
    showCaixa(g);
    showSelected(g);
    
    g.setColor(Color.red);
    
    g.drawLine(supX,supY,supX+size,supY);
    g.drawLine(supX,supY+(int)(size * tabYPercent),size,supY+(int)(size * tabYPercent));    
    g.drawLine((int)(size * tabXPercent),supY,(int)(size * tabXPercent),supY+size);    
    
  }//fim paint
  
 public void show(){}
 
 
     //LISTENERS
  
  public void mouseClicked(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mousePressed(MouseEvent e)
  {
    int rX=0;
    int rY=0;
    int tx=e.getX()/quadTam;
    int ty=e.getY()/quadTam;
		
      // CLICKA NA CAIXA DE PECAS
      if((e.getX()<quadCPTam*nQuadsColunaCaixa)&&(e.getY()>supCPY))
      { 
      	int op=0;
      	             
        rX = (e.getX()/quadCPTam );
        rY = ((e.getY()-supCPY)/quadCPTam);        
        
        op = rY * nQuadsColunaCaixa+rX;
        if(op>=(tab.getCaixa()).getN())
        	op=(tab.getCaixa()).getSelectedPos();
        
        tab.getCaixa().select(op);
      }
      else //CLICKA NA ZONA SELECTED
        if(e.getX()>selectedSupX && e.getY()>selectedSupY)
        {
          switch(e.getModifiers())
          {
            case(e.BUTTON1_MASK):((tab.getCaixa()).getSelected()).shiftLeft();break;
            case(e.BUTTON3_MASK):((tab.getCaixa()).getSelected()).shiftRight();break;
          }
        }      
      	else
      		//CLICKA NO TABULEIRO
      		if(e.getY()<supCPY && e.getX()<selectedSupX)
      		{
      			if(tab.haJogadasValidas())
      			{
      			  	if(j.playHuman(tx,ty,((tab.getCaixa()).getSelectedPos())))
      					{
      						if(j.tipo==Jogo._1ONCOMP)
      						{
      							if(tab.haJogadasValidas())
      							{
      							 j.playPC();
      							}
      						}
      					}
      			}
      		}
      repaint();
  }
}// fim da class+