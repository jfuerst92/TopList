# TopList

By: Joseph Fuerst

WORK IN PROGRESS

An android app that allows you to rate, and get rated by other users based on a selfie that you upload. Uses Google App engine 
and Google Datastore to handle user accounts, and Amazon S3 to store user images. 

TODO:

Implement secure user authentication. (Current implementation is a placeholder and NOT MEANT for use. It is completely unsecured.)

SOLVED: Lower SDK requirement. (I've put it abnormally high. This was a simple fix.)

SOLVED: Change method of image loading so that users never encounter a loading image. (Access S3 images through cloudfront, rather than directly from the S3 bucket)

Make image upload mandatory. (Image upload activity included during registration.)

Implement aesthetic and usability improvements. (Make it look nice. Make sure users don't get frustrated/confused)

Implement leaderboards


