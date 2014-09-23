import java.util.ArrayList;

/**
 * A k-means clustering algorithm implementation.
 * 
 */

public class KMeans {

	//Distortion initially set to an arbitrary value.
	private double distortion = 9999;
	private double lastDistortion;

	public KMeansResult cluster(double[][] centroids, double[][] instances,
			double threshold) {

		//changes the centroid location until the threshold is reached.
	
		while(distortion > threshold){
			System.out.println("hi");
			centroids = assignCentroids(centroids,instances);
		}
		//centroids = assignCentroids(centroids, instances);
		return null;
	}

	/*
	 * Reassigns all points to a new centroid and calls the moveCentroids 
	 * function
	 */	private double[][] assignCentroids(double[][] centroids, 
			 double[][] instances){
		 System.out.println("assignCentroids");
		 //Each row represents a centroid.
		 //Each column represents a row in instances[][].
		 int[][] assignments = new int[centroids.length][instances.length];
		 int[] nextFreeAssignment = new int[centroids.length];

		 //Array for holding the difference between a centroid and an instance.
		 double[][]differences = new double[centroids.length][instances[0].length];
		 for(int h = 0; h < instances.length; h++){
			 for(int i = 0; i < centroids.length; i++){
				 for (int j = 0; j < centroids[i].length; j++){
					 differences[i][j] = centroids[i][j] - instances[h][j];
				 }		 
			 }
			 
			 int closestCentroid = getClosest(differences);
			 assignments
			 [closestCentroid][nextFreeAssignment[closestCentroid]++] = h;

		 }

		 assignments = findOrphans(assignments, centroids, instances);
		 centroids  = moveCentroids(centroids, assignments, instances);

		 return centroids;
	 }//end method

	 /*
	  * Moves all the centroids according to their new assignments and finds the 
	  * distortion for all the centroid movements.
	  */
	 private double[][] moveCentroids(double[][] centroids,
			 int assignments[][], double[][] instances){
		 System.out.println("moveCentroids");
		 //lengths[] stores how many features are attached to a centroid
		 int[] lengths = new int[centroids.length];
		 for(int i = 0; i < assignments.length; i++){
			 for(int j = 0; j < assignments[i].length; j++){
				 if (assignments[i][j] != 0){
					 lengths[i] = lengths[i]++;
				 }
			 }
		 }

		 double[][] newFeaturePosition = new double[centroids.length]
				 [instances[0].length];
		 /*
		  * Iterates through the assignments row first, which denotes centroids.
		  * Then Iterates across the columns of each row, which indicate a feature
		  * column. Then iterates through all the columns all the features of that
		  * centroid have to get the sum of all the feature's lengths.
		  */
		 for (int i = 0; i < assignments.length; i ++){
			 for (int j  = 0; j < assignments[i].length; j++){
				 int instanceRow = assignments[i][j];
				 for(int k = 0; k < instances[instanceRow].length; k++){
					 newFeaturePosition[i][k] = newFeaturePosition[i][k] +
							 instances[instanceRow][k];
				 }
			 }
		 }
		 
		 //divides the sum of the distances to get the average.
		 for(int i = 0; i < newFeaturePosition.length; i++){
			 for (int j = 0; j < newFeaturePosition[i].length; j++){
				 newFeaturePosition[i][j] = newFeaturePosition[i][j] / lengths[i];
			 }
		 }
		 
		 //finds the distortion
		 if (distortion != 9999){
			 lastDistortion = distortion;
		 }
		 
		 for(int i = 0; i < centroids.length; i ++){
			 for(int k = 0; k < centroids[i].length; k++){
				 distortion = Math.abs(centroids[i][k] - newFeaturePosition[i][k]);
			 }
		 }
		 if (lastDistortion != 0){
			 distortion = (distortion - lastDistortion) / lastDistortion;
		 }
		 return newFeaturePosition;
	 }

	 /*
	  * Returns whatever centroid is closest to the instance.
	  * The centroid is indicated by its row.
	  */
	 private int getClosest(double[][] differences){
		 System.out.println("getClosest");
		 //Array to store the distances between each centroid and instance
		 double[] totalDistance = new double[differences.length];

		 //Will indicate which element of totalDistance is the smallest
		 int indicator = 0;

		 //Iterates through differences[][], adding each difference to the next
		 for (int i = 0; i < differences.length; i++){
			 for (int j = 0; j < differences[i].length; j++){
				 totalDistance[i] = Math.abs(totalDistance[i] + differences[i][j]);
			 }
			 //System.out.println("Total Distance: "+ totalDistance[i]);
		 }


		 double min = totalDistance[0];

		 //Sees which element in totalDistance is the smallest
		 for (int i = 0; i < totalDistance.length; i++){
			 if (totalDistance[i] < min ){
				 indicator = i;
				 min = totalDistance[i];
			 }//end if
		 }//end for
		 return indicator;
	 }//end method

	 /*
	  * Searhces through the assignments array and looks to see if any of the 
	  * rows are empty.
	  */
	 private int[][] findOrphans(int assignments[][], double centroids[][],
			 double instances[][]){

		 boolean hasOrphans = false;
		 int orphanedCentroid = 0;
		 double distance = 0;
		 double max = 0;
		 int instance = 0;

		 for (int i = 0; i < assignments.length; i ++){
			 if (assignments[i][0] == 0 && assignments[i][1] == 0){
				 hasOrphans = true;
				 orphanedCentroid = i;
			 }//end if 
		 }//end for

		 if (hasOrphans){

			 //Searches through each instance, comparing the length between it
			 //and the centroid
			 for (int i = 0; i < instances.length; i++){
				 for (int j = 0; j < instances[i].length; j++){
					 distance = centroids[orphanedCentroid][j] - instances[i][j];
				 }//end for

				 //checks if the distance between this instance and the centroid
				 //is the greatest
				 if (distance > max){
					 max = distance;
					 instance = i;
				 }//end if

				 //resets the distance
				 distance = 0;
			 }//end for

			 //Assigns the instance to the Centroid.
			 assignments[orphanedCentroid][0] = instance;

			 /*
			  * Searches assignments for the instance we just added to the 
			  * orphaned centroid, deleting that instance from the centroid
			  * it previously belonged to.
			  */
			 for (int i = 0; i < assignments.length; i ++){

				 //checks to see if the centroid we are looking at is the one
				 //we just put somethign in
				 if (assignments[i][0] == instance){
					 i++;
				 }//end if

				 //
				 for (int j = 0; j < assignments[i].length; j ++){
					 if (assignments[i][j] == instance){

						 //checks to see if the reassigned instance is the last
						 //element in assignments[i].
						 if (j == assignments[i].length -1){
							 assignments[i][j] = 0;
						 }//end if 

						 //Shifts all further elements in assignments[i] one 
						 //position to the left
						 for (int k = j; k < assignments[i].length-1; k++){
							 assignments[i][k] = assignments[i][k+1];
						 }//end for
					 }//end if
				 }//end for
			 }//end if
		 }//end if
		 return assignments;
	 }//end findOrphans
}
