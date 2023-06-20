import java.util.ArrayList;

public class MyArrayList<T> extends ArrayList<T>
{
	public String getBuchungenAsText()
	{
		String text ="";
		for (int i=0;i<this.size();i++)
		{
			text += ((Buchungszeile)this.get(i)).getTextZusammenfassung()+"\n";			
			
		}
		return text;
	}
}
