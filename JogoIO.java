import java.io.*;

class JogoIO
{
	private Jogo j;
	
	public JogoIO(){}
	public JogoIO(Jogo j){this.j=j;}
	
	public void save(String fname)
	{
		try
		{
			FileOutputStream fos=new FileOutputStream(fname);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(j);
			oos.close();
		}
		catch(IOException e)
		{
			System.out.println("Ocorreu um problema ao usar o ficheiro");
			System.out.println(e);
		}
	}
	
	public Jogo load(String fname)
	{
		Jogo j=null;
		
		try
		{
			FileInputStream fos=new FileInputStream(fname);
			ObjectInputStream oos=new ObjectInputStream(fos);
			j=(Jogo)(oos.readObject());
			oos.close();
		}
		catch(IOException e)
		{
			System.out.println("Ocorreu um problema ao usar o ficheiro");
		  System.out.println(e);
		}
		catch(Exception ex)
		{
			System.out.println("Ocorreu um problema inesperado ao tentar abrir o ficheiro");
			System.out.println(ex);
		}
		
		return(j);
	}
}