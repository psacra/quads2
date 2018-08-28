import java.io.*;

class Jogo implements Serializable
{
	public static final int _1ON1=0;
	public static final int _1ONCOMP=1;
	public static final int SOLO=2;
	public static final int JOGADOR1=1;
	public static final int JOGADOR2=-1;
	
	public int tipo;
	private int currPlayer=1;
	private Tabuleiro lastTab;
	private Tabuleiro tab;
	
	public Jogo(int tipo,int tam,double perExp,int PecasIn)//tipo=_1ON1 ou _1ONCOMP; tam minimo=6
	{
		final int NPADROES=5;
		
		CaixaPecas cp=new CaixaPecas(tam*tam);
		cp.geraRandom(NPADROES);
		tab=new Tabuleiro(tam,cp);
		lastTab=tab;
		tab.init(PecasIn);
		cp.setExplosivas((int)Math.round(cp.getN()*perExp));
		this.tipo=tipo;
	}
	
	public void playPC()
	{
		int [] jogada;
		jogada=getPlay();
		
    for(int cnt=Quad.UP;cnt<=Quad.LEFT;cnt++)
    {
      if(tab.colocavel(jogada[0],jogada[1],(tab.getCaixa()).getVect()[jogada[2]]))  
      {
        tab.addQuad(jogada[0],jogada[1],(tab.getCaixa()).getVect()[jogada[2]]);
		    (tab.getCaixa()).removeQuad(jogada[2]);
      }
      else
        (((tab.getCaixa()).getVect())[jogada[2]]).shiftRight();
    }
		
    if(tab.getQuad(jogada[0],jogada[1]).isExplosiva())
			tab.explode(jogada[0],jogada[1]);
	}
	public boolean playHuman(int col,int lin,int cpIndex)//cpIndex é a posição da peça a jogar na CaixaPecas
	{
		boolean res=false;
		lastTab=new Tabuleiro(tab);
		
		if(tab.colocavel(col,lin,(tab.getCaixa()).getQuad(cpIndex)))
    {
     tab.addQuad(col,lin,(tab.getCaixa()).getQuad(cpIndex));
     (tab.getCaixa()).removeQuad((tab.getCaixa()).getSelectedPos());
       res=true;
       if(tab.getQuad(col,lin).isExplosiva())
  		 {
  				tab.explode(col,lin);
  	   }
   	}
   	return(res);
	}
	public void hint()
	{
		playPC();
		try{Thread.sleep(1000);}catch(Exception e){}
		undo();
	}
	public void undo()
	{
		this.tab=this.lastTab;
	}
	
	public Tabuleiro getTabuleiro(){return tab;}
	
	private int[] getPlay()
	{
		//As jogadas possiveis serão colocadas num array[][3] que
		//terá capacidade para num_peças_na_caixa*4*num_casas_do_tabuleiro jogadas, cada
		//uma com três campos (col,lin,indice do quad na cp)
		
		int [][] jogadas=new int[((tab.getCaixa()).getN())*tab.getN()*tab.getN()*Quad.NPARTES][3];
		int index=0;
			
			//Recolha de jogadas possiveis
			
			for(int a=0;a<tab.getN();a++)
			{
				for(int b=0;b<tab.getN();b++)
				{
					for(int i=0;i<(tab.getCaixa()).getN();i++)
					{
						for(int unCnt=Quad.UP;unCnt<=Quad.LEFT;unCnt++)
						{
							if(tab.colocavel(a,b,(tab.getCaixa()).getQuad(i)))
							{
								jogadas[index][0]=a;
								jogadas[index][1]=b;
								jogadas[index][2]=i;
								index++;
							}
							((tab.getCaixa()).getVect()[i]).shiftRight();
						}
					}
				}
			}//Fim da recolha das jogadas possiveis
				
			int aux=0;//Vai ter para cada jogada o indice da peça na cp
			
			for(int i=0;i<index;i++)
			{
				aux=jogadas[i][2];
				for(byte cnt=Quad.UP;cnt<=Quad.LEFT;cnt++)
				{
					if(((tab.getCaixa()).getQuad(aux)).getParte(cnt)==(tab.getCaixa()).lessPattern(4) && tab.colocavel(jogadas[i][0],jogadas[i][1],(tab.getCaixa()).getQuad(jogadas[i][2])))
						return(jogadas[i]);
					else
						((tab.getCaixa()).getVect())[jogadas[i][2]].shiftRight();
				}
			}
			//No final deste método, o computador sabe onde vai jogar
			//e que peça vai jogar, mas não a posição dessa peça
			//Essa vai ser colocada na primeira posição possivel
			
			return(jogadas[(int)Math.random()*index]);
	}
}