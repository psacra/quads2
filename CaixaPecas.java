import java.io.*;

class CaixaPecas implements Serializable
{
	private int n;

	private Quad [] vect;

  private int selected=0;

	public CaixaPecas(int n)
	{

		vect=new Quad[n];

		this.n=n;

		for (int cnt=0;cnt<n;cnt++)

				vect[cnt] = new Quad ();
	}		

	public void geraRandom(int nHip)//nHipoteses min 4;
	{
		for(int cnt=0;cnt<n;cnt++)
		{
			vect[cnt]=new Quad(nHip);
		}
		
		for(int cnt=0;cnt<n;cnt++)
		{
			while(isHere(new Quad(vect[cnt]),cnt))
			{
				vect[cnt]=new Quad(nHip);
			}
		}
	}
	
	private boolean isHere(Quad q, int tillWhere)
	{
		for(int cnt=0;cnt<tillWhere;cnt++)
		{
			if(vect[cnt].eIgual(q))
				return true;
		}		
		return false;
	}

	public void setExplosivas(int nExp)
	{
		int aux=0;
		int i=0;

		do
		{
			aux=(int)(Math.random()*getN());
			
			if(!vect[aux].isExplosiva())
			{
				vect[aux].setExplosiva(true);
				i++;
			}
		}
		while(i<nExp);
	}

	public int getSelectedPos()
	{return(selected);}

	public void select(int s){selected=s;}

	public Quad getSelected(){return vect[selected];}

	public void removeQuad(int pos)//remove peça e alinha a tabela
	{
	  Quad [] tmpVect = new Quad [n-1]	;

		System.arraycopy(vect,0,tmpVect,0,pos);

		System.arraycopy(vect,pos+1,tmpVect,pos,(n-pos)-1);

		vect = tmpVect;

		n--;
		
		if(selected==n)
			selected--;
	}

	public void addQuad(Quad q)
	{
		Quad [] tmpVect = new Quad [n+1];
		
		System.arraycopy(vect,0,tmpVect,0,n);

		tmpVect[n]=q;		

		vect = tmpVect;

		n++;
	}
	
	public boolean isSelected(int pos)
  {

     if (pos==selected)
     	return true;
     	
    return false;
  }
  
	public int getN()
	{
		return(n);
	}

	public Quad getQuad(int pos)
  {
  	return (new Quad(vect[pos]));
  }

	public Quad getAndRemoveLastQuad()
  {	
  	Quad res =new Quad(vect[n-1]);

  	removeQuad(n-1);

  	return (res);
  }	
  
  //Vai devolver o número do tipo de padrões associado ao 
  //valor whatPattern existentes na caixa num determinado momento
  
  private int getNumPatterns(int whatPattern)
	{
		int res=0;
		
		for(int cnt=0;cnt<n;cnt++)
			for(byte cntpartes=Quad.UP;cntpartes<=Quad.LEFT;cntpartes++)
				if(vect[cnt].getParte(cntpartes)==whatPattern)
					res++;
					
		return(res);
	}

	//Servirá para o computador decidir qual a melhor jogada
  //Devolve o padrão que menos abunda na caixa de peças
  
  public int lessPattern(int numPatterns)
	{
		int menor=getNumPatterns(0);
		int padrao=0;
		
		for(int i=1;i<numPatterns;i++)
		{
			if(menor>getNumPatterns(i))
			{
				menor=getNumPatterns(i);
				padrao=i;
			}
		}
		
		return(padrao);
	}
	
	public Quad[] getVect()
	{
		return(vect);
	}
}