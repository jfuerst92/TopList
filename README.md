# TopList

By: Joseph Fuerst

WORK IN PROGRESS

An android app that allows you to rate, and get rated by other users based on a selfie that you upload. Uses Google App engine 
and Google Datastore to handle user accounts, and Amazon S3 to store user images. 

TODO:

Implement secure user authentication. (Current implementation is a placeholder and NOT MEANT for use. It is completely unsecured.)

Lower SDK requirement. (I've put it abnormally high. This is a simple fix.)

Change method of image loading so that users never encounter a loading image. (create queue of images that load in the background)

Make image upload mandatory. (Image upload activity included during registration.)

Implement aesthetic and usability improvements. (Make it look nice. Make sure users don't get frustrated/confused)

Switch database from ndb to a SQL database for the purposes of better relational management. (necessary as cost-cutting measure and better implementation of leaderboards.)

Implement leaderboards


