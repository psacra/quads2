import java.io.*;

class Tabuleiro implements Serializable
{

	private int n;

	private Quad [][] tab;

	private CaixaPecas cp;

	public Tabuleiro(int n, CaixaPecas cp)
	{

		this.n=n;		

		this.cp = cp;

		tab = new Quad[n][n];

		for (int cntX=0;cntX<n;cntX++)

				for (int cntY=0;cntY<n;cntY++)

					tab[cntX][cntY]= new Quad();

	}
	
	public Tabuleiro(Tabuleiro t)
	{
		this.n=t.n;
		this.tab=new Quad[n][n];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				this.tab[i][j]=new Quad(t.tab[i][j]);
			}
		}
		this.cp=new CaixaPecas((t.cp).getN());
	
		for(int a=0;a<(t.cp).getN();a++)
		{
			((this.cp).getVect())[a]=(t.cp).getQuad(a);
		}
	}	

	public boolean colocavel(int col,int lin,Quad q)
	{

		byte nCorrect=0;//tocam correctamente

		//byte nErrados=0;//tocam mal

		byte nVazios =0;//na tocam em nada

		byte nParedes=0;//toca no "lado" do tabuleiro

		if (!getQuad(col,lin).isEmpty())

			return false;

		if (col+1==n)nParedes++;

		else

			if (tab[col+1][lin].isEmpty())nVazios++;

			else

				if (tab[col+1][lin].getLeft()==q.getRight())nCorrect++;

				else

					return false;

					//nErrados++;

		if (col==0)nParedes++;

		else

			if (tab[col-1][lin].isEmpty())nVazios++;

			else

				if (tab[col-1][lin].getRight()==q.getLeft())nCorrect++;

				else

					return false;

					//nErrados++;

		if (lin+1==n)nParedes++;

		else

			if (tab[col][lin+1].isEmpty())nVazios++;

			else

				if (tab[col][lin+1].getUp()==q.getDown())nCorrect++;

				else

					return false;

					//nErrados++;  	

		if (lin==0)nParedes++;

		else

			if (tab[col][lin-1].isEmpty())nVazios++;

			else

				if (tab[col][lin-1].getDown()==q.getUp())nCorrect++;

				else

					return false;

					//nErrados++;


  	//if (nErrados >0)return false;

  	if (nVazios+nParedes==Quad.NPARTES)return false;

  	return true;

	}	

	public boolean isEmpty()
	{
		for(int cntx=0;cntx<n;cntx++)
			for(int cnty=0;cnty<n;cnty++)
				if(!tab[cntx][cnty].isEmpty())
					return false;
		
		return true;
	}

  public void addQuad(int col,int lin,Quad q)

  {

  	tab[col][lin]=new Quad(q);

  }



  public void explode(int col, int lin)//Retira explosividade à peça colocada,retira peças do tabuleiro e coloca-as na caixa de peças
  {
			tab[col][lin].setExplosiva(false);
			
			cp.addQuad(new Quad(tab[col][lin]));
			
			tab[col][lin].resetQuad();

			if(!(col==0) && !tab[col-1][lin].isEmpty())
			{
				cp.addQuad(new Quad(tab[col-1][lin]));

				tab[col-1][lin].resetQuad();
			}

			if(!(col==n-1) && !tab[col+1][lin].isEmpty())
			{
				cp.addQuad(new Quad(tab[col+1][lin]));

				tab[col+1][lin].resetQuad();
			}

			if(!(lin==0) && !tab[col][lin-1].isEmpty())
			{
				cp.addQuad(new Quad(tab[col][lin-1]));

				tab[col][lin-1].resetQuad();		
			}

			if(!(lin==n-1) && !tab[col][lin+1].isEmpty())
			{
				cp.addQuad(new Quad(tab[col][lin+1]));

				tab[col][lin+1].resetQuad();		
 	 		}
  }

  public boolean haJogadasValidas()

  {

  	Quad aux;

  	

		for (int cntc=0;cntc<n;cntc++)

		{

			for (int cntl=0;cntl<n;cntl++)			

			{

				if(tab[cntc][cntl].isEmpty())

				{

				 for (int cntPecas=0;cntPecas<cp.getN();cntPecas++)

				 {

					aux=new Quad(cp.getQuad(cntPecas));

				 	for(int unCnt=Quad.UP;unCnt<=Quad.LEFT;unCnt++)//unCnt=Unused Counter

					{

						if(colocavel(cntc,cntl,aux))

						   return true;

						else

						   aux.shiftRight();

					}

				 }		

				}

			}	

		}

	return false;

   }

	

	public int getN()

	{

		return(n);

	}

	

	public Quad getQuad(int x,int y)

	{

		return(new Quad(tab[x][y]));

	}



  public CaixaPecas getCaixa()

  {

  	return cp;

  }

	

	private boolean alone(int rx,int ry)

  	{

  		boolean upOK    = false;

  		boolean rightOK = false;

  		boolean downOK  = false;

			boolean leftOK  = false;

			

			if(getQuad(rx,ry).isEmpty())

			{

  			if (ry==0) upOK = true;

  	 		else if (getQuad(rx,ry-1).isEmpty()) upOK = true;

  	

  			if (rx==0) leftOK = true;

  	 		else if (getQuad(rx-1,ry).isEmpty()) leftOK = true;



	  		if (ry==n-1) downOK = true;

	  	 	else if (getQuad(rx,ry+1).isEmpty()) downOK = true;

  	

  			if (rx==n-1) rightOK = true;

  	 		else if (getQuad(rx+1,ry).isEmpty()) rightOK = true;

  		}

  				 

  		return (upOK && rightOK && downOK && leftOK);

  	}

	

	private void addRandomQuad()

  	{

  	int rx;

  	int ry;

  	Quad q = cp.getAndRemoveLastQuad();

  	

  	do

  	{

		rx=(int)(Math.random()*n);

		ry=(int)(Math.random()*n);		

	}

	while (!colocavel(rx,ry,q) && !alone(rx,ry));

		

	  addQuad(rx,ry,q);	

	  

	}

		

	public void init(int nPecasIniciais)//minimo=2

  	{ 

   	  for (int a=0;a<nPecasIniciais;a++)

   	  {

  	    addRandomQuad();

  	  } 	

  	}

  

}//************* fim class *************