import webapp2
from google.appengine.api import oauth

app = webapp2.WSGIApplication([
    

], debug=True)
app.router.add(webapp2.Route(r'/user', 'user.User')) # add user
app.router.add(webapp2.Route(r'/user/<id:[0-9]+><:/?>', 'user.User')) #get a specific user
app.router.add(webapp2.Route(r'/userImg/<uid:[0-9]+><:/?>', 'user.UserImage')) #add image key to user
app.router.add(webapp2.Route(r'/myimagehandler', "image.MyImageHandler"))
#app.router.add(webapp2.Route(r'/login', ""))
app.router.add(webapp2.Route(r'/usersearch', "user.UserSearch"))
app.router.add(webapp2.Route(r'/uprate/<uid:[0-9]+><:/?>', "user.upRate"))
app.router.add(webapp2.Route(r'/dnrate/<uid:[0-9]+><:/?>', "user.dnRate"))
app.router.add(webapp2.Route(r'/userDel/<id:[0-9]+><:/?>', 'user.User')) #Delete a user

