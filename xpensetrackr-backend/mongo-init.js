db = db.getSiblingDB('xpensetrackr-db');

db.createUser({
    user: 'admin',
    pwd: '@dmin',
    roles: [
        {
            role: 'readWrite',
            db: 'xpensetrackr-db'
        }
    ]
});