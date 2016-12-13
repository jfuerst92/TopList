from google.appengine.ext import ndb

class Model(ndb.Model):
    def to_dict(self):
        d = super(Model, self).to_dict()
        d['key'] = self.key.id()
        return d

        
class User(ndb.Model):
    fName = ndb.StringProperty(required=True)
    email = ndb.StringProperty(required=True)
    password = ndb.StringProperty(required=True)
    upRates = ndb.IntegerProperty(required=True)
    dnRates = ndb.IntegerProperty(required=True)
    
    city = ndb.StringProperty(required=True)
    state = ndb.StringProperty(required=True)
    gender = ndb.StringProperty(required=True)
    
    image = ndb.StringProperty()
    
    def to_dict(self):
        g = super(User, self).to_dict()
        #g['groups'] = [g.id() for m in g['groups']]
        g['key'] = self.key.id()
        return g
   
   
class Group(ndb.Model):
    members = ndb.KeyProperty(kind='User', repeated=True)
    description = ndb.StringProperty(required=True)
    
    
    