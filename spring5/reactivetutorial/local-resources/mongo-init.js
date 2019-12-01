db = db.getSiblingDB('JavaReactiveTutorial');
db.createUser(
   {
     user: "basicusr",
     pwd: "passbsusr",
     roles: [ { role: "readWrite", db:"JavaReactiveTutorial" }]
   }
);
db.item.insertOne(
   {
     description: "user",
     price: "1.1"
   }
);