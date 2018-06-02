import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class theMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			////////////////////// Step 0 /////////////////////
			Scanner inFile = new Scanner(new FileReader(args[0]));
			Scanner kb = new Scanner(System.in);
			BufferedWriter outFile1 = new BufferedWriter(new FileWriter(new File(args[1])));
			BufferedWriter outFile2 = new BufferedWriter(new FileWriter(new File(args[2])));
			BufferedWriter outFile3 = new BufferedWriter(new FileWriter(new File(args[3])));
			DecimalFormat df = new DecimalFormat("#.####");
			
			if(args.length < 1) 
		    {
		        System.out.println("Error");
		        System.exit(1);
		    }
			
			int numRows = inFile.nextInt();
			int numCols = inFile.nextInt();
			int minVal = inFile.nextInt();
			int maxVal = inFile.nextInt();
			int label = inFile.nextInt();
			
			System.out.println("Input K (length of neighborhood to be used in the K-curvature computation): ");	
			int K = kb.nextInt();
			arcChord arc = new arcChord();
			arc.countPts(inFile);
			arc.chordLength = 2 * K;
			arc.PtAry = new boundaryPt[arc.numPts];
			arc.chordAry = new double[arc.chordLength];
			
			for(int i = 0; i < arc.numPts; i++)
			{
				arc.PtAry[i] = new boundaryPt();
			}
			
			for(int i = 0; i < arc.chordLength; i++)
			{
				arc.chordAry[i] = 0;
			}
			
			inFile = new Scanner(new FileReader(args[0]));
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			label = inFile.nextInt();
			int x, y;
			int index = 0;
			
			outFile1.write(numRows + " " + numCols + " " + minVal + " " + maxVal +"\n" );
			outFile1.write(label + "\n");
			outFile1.write(arc.numPts + "\n");
			while(inFile.hasNext())
			{
				x = inFile.nextInt();
				y = inFile.nextInt();
				arc.loadData(x, y, index);
				index++;
			}
			
		
			//////////////////////Step 1 /////////////////////
			arc.P1 = 0;
			arc.P2 = arc.chordLength - 1;
			
			while(arc.P2 != (arc.chordLength / 2)) // Step 9
			{
				//////////////////////Step 2 /////////////////////
				index = 0;
				int currPt = arc.P1;
				
				//////////////////////Step 3,4 /////////////////////
				while(index < arc.chordLength)
				{
					double dist = arc.computeDistance(arc.P1, arc.P2, currPt);
					arc.chordAry[index] = dist;
					index++;
					currPt = (currPt + 1) % arc.numPts;
				}
				
				//////////////////////Step 5 /////////////////////
				for(int i = 0; i < arc.chordLength; i++)
				{
					outFile3.write(df.format(arc.chordAry[i]) + "\n");
				}
				outFile3.write("---------------------" + "\n");	
				
				//////////////////////Step 6 /////////////////////
				int maxIndex = arc.findMax();
				int whichIndex = (arc.P1 + maxIndex) % arc.numPts;	
				arc.PtAry[whichIndex].maxVotes++;
				
				if(arc.PtAry[whichIndex].maxDist < arc.chordAry[maxIndex])
				{
					arc.PtAry[whichIndex].maxDist = arc.chordAry[maxIndex];
				}
				//////////////////////Step 7 /////////////////////
				for(int i = arc.P1; i <= arc.P2; i++)
				{
					outFile3.write(arc.PtAry[i].x + " " + arc.PtAry[i].y + " "+ df.format(arc.PtAry[i].maxDist) + "\n");
				}
			
				//////////////////////Step 8 /////////////////////
				arc.P1 = (arc.P1 + 1) % arc.numPts;
				arc.P2 = (arc.P2 + 1) % arc.numPts;
			}
			
			//////////////////////Step 10 /////////////////////
			outFile3.write("---------------------" + "\n");
			arc.printPtAry(outFile3);	
			
			//////////////////////Step 11, 12 /////////////////////
			for(int i = 0; i < arc.numPts; i++)
			{
				arc.PtAry[i].isLM = arc.computeLocalMaxima(i);		
			}
			
			for(int i = 0; i < arc.numPts; i++)
			{
				arc.PtAry[i].corner = arc.setCorner(i);
			}
			//////////////////////Step 13 /////////////////////
			
			for(int i = 0; i < arc.PtAry.length; i++)
			{
				outFile1.write(" "+arc.PtAry[i].x + " " + arc.PtAry[i].y + " " + arc.PtAry[i].corner + "\n");
			}
			
			//////////////////////Step 14 /////////////////////
			image img = new image(numRows, numCols, minVal, maxVal);
			
			//////////////////////Step 15 /////////////////////
			img.plotPt2Img(arc.PtAry, arc.numPts);

			//////////////////////Step 16 /////////////////////
			img.prettyPrint(outFile2);
			
			
			//////////////////////Step 17 /////////////////////
		    inFile.close();
		    outFile1.close();
		    outFile2.close();
		    outFile3.close();
		} 	
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	
	}

}
