import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class arcChord
{
	int chordLength;
	int countPts;
	boundaryPt[] PtAry;
	int numPts;
	double[] chordAry;
	int P1;
	int P2;
	
	arcChord()
	{
		chordLength = 0;
		countPts = 0;
		numPts = 0;
		PtAry = new boundaryPt[numPts];
		chordAry = new double[chordLength];
		P1 = 0;
		P2 = chordLength;
	}
	
	
	void loadData(int x, int y, int index)
	{
		PtAry[index].x = x;
		PtAry[index].y = y;
	}
	
	double computeDistance(int P1, int P2, int P)
	{        
		double result = 0;
		double A = (double)PtAry[P2].y - (double)PtAry[P1].y;
		double B = (double)PtAry[P1].x - (double)PtAry[P2].x;
		double C = (double)PtAry[P2].x * (double)PtAry[P1].y - (double)PtAry[P1].x *  (double)PtAry[P2].y;
		double numerator = (Math.abs(A * (double) PtAry[P].x + B * (double) PtAry[P].y + C));
		double denominator = (Math.sqrt(A*A + B*B));
		result = numerator/ denominator;
		return result;
	}
	
	int findMax()
	{	
		double max = chordAry[0];
		int index = 0;
		for(int i = 1; i < chordAry.length; i++)
		{
			if(chordAry[i] > max)
			{
				max = chordAry[i];
				index = i;
			}
		}
		return index;
	}
	
	boolean computeLocalMaxima(int index)
	{
		for(int i = index - 3; i <= index + 3; i++)
		{
            if(PtAry[(i + numPts) % numPts].maxVotes > PtAry[index].maxVotes)
            {
                return false;
            }
        }
        return true;
	}

	int setCorner(int index)
	{
		 if(PtAry[index].isLM == true && PtAry[(index+numPts - 1) % numPts].maxDist < PtAry[index].maxDist
		           && PtAry[(index + 1) % numPts].maxDist < PtAry[index].maxDist)
		 {
		     return 9;
		 }
		 return 1;
	}
	void countPts(Scanner inFile)
	{
		int x = 0, y = 0;
		while(inFile.hasNext())
		{
			x = inFile.nextInt();
			y = inFile.nextInt();
			numPts++;
		}
		inFile.close();
	}
	
	
	void printPtAry(BufferedWriter outFile)
	{
		try
		{
			int counter = 0;
			for(int i = 0; i <= numPts - 1; i++)
			{
				if(counter < 5)
				{
					outFile.write(i + " " + PtAry[i].x + " " + PtAry[i].y + "\n");
					counter++;
				}
				else
				{
					counter = 0;
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
