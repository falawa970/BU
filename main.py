from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)
app.secret_key = os.environ.get("SESSION_SECRET", "default_secret_key")

# Configure database
app.config['SQLALCHEMY_DATABASE_URI'] = os.environ.get("DATABASE_URL")
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# Model definitions
class Livre(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    titre = db.Column(db.String(100), nullable=False)
    auteur = db.Column(db.String(100), nullable=False)
    isbn = db.Column(db.String(20), unique=True)
    description = db.Column(db.Text)
    annee_publication = db.Column(db.Integer)
    disponible = db.Column(db.Boolean, default=True)
    date_creation = db.Column(db.DateTime, server_default=db.func.now())
    date_modification = db.Column(db.DateTime, server_default=db.func.now(), onupdate=db.func.now())
    
    def to_dict(self):
        return {
            'id': self.id,
            'titre': self.titre,
            'auteur': self.auteur,
            'isbn': self.isbn,
            'description': self.description,
            'annee_publication': self.annee_publication,
            'disponible': self.disponible,
            'date_creation': self.date_creation.isoformat() if self.date_creation else None,
            'date_modification': self.date_modification.isoformat() if self.date_modification else None
        }

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50), nullable=False)
    prenom = db.Column(db.String(50), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    telephone = db.Column(db.String(20))
    type = db.Column(db.String(20), nullable=False)  # ADMIN, CLIENT, etc.
    
    def to_dict(self):
        return {
            'id': self.id,
            'nom': self.nom,
            'prenom': self.prenom,
            'email': self.email,
            'telephone': self.telephone,
            'type': self.type
        }

class Reservation(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    livre_id = db.Column(db.Integer, db.ForeignKey('livre.id'), nullable=False)
    date_reservation = db.Column(db.DateTime, server_default=db.func.now())
    date_debut = db.Column(db.Date, nullable=False)
    date_fin = db.Column(db.Date, nullable=False)
    status = db.Column(db.String(20), nullable=False)  # RESERVEE, EMPRUNTEE, RETOURNEE, ANNULEE
    date_retour = db.Column(db.Date)
    
    user = db.relationship('User', backref=db.backref('reservations', lazy=True))
    livre = db.relationship('Livre', backref=db.backref('reservations', lazy=True))
    
    def to_dict(self):
        return {
            'id': self.id,
            'user_id': self.user_id,
            'livre_id': self.livre_id,
            'date_reservation': self.date_reservation.isoformat() if self.date_reservation else None,
            'date_debut': self.date_debut.isoformat() if self.date_debut else None,
            'date_fin': self.date_fin.isoformat() if self.date_fin else None,
            'status': self.status,
            'date_retour': self.date_retour.isoformat() if self.date_retour else None,
            'user': self.user.to_dict() if self.user else None,
            'livre': self.livre.to_dict() if self.livre else None
        }

# Create database tables
with app.app_context():
    db.create_all()

# REST API Endpoints

# Livres
@app.route('/api/livres', methods=['GET'])
def get_all_livres():
    livres = Livre.query.all()
    return jsonify([livre.to_dict() for livre in livres])

@app.route('/api/livres/<int:id>', methods=['GET'])
def get_livre(id):
    livre = Livre.query.get_or_404(id)
    return jsonify(livre.to_dict())

@app.route('/api/livres/disponibles', methods=['GET'])
def get_livres_disponibles():
    livres = Livre.query.filter_by(disponible=True).all()
    return jsonify([livre.to_dict() for livre in livres])

# Réservations
@app.route('/api/reservations/<int:id>', methods=['GET'])
def get_reservation(id):
    reservation = Reservation.query.get_or_404(id)
    return jsonify(reservation.to_dict())

@app.route('/api/reservations', methods=['POST'])
def create_reservation():
    data = request.get_json()
    
    # Validation de base
    required_fields = ['user_id', 'livre_id', 'date_debut', 'date_fin']
    for field in required_fields:
        if field not in data:
            return jsonify({'error': f'Le champ {field} est requis'}), 400
    
    user = User.query.get(data['user_id'])
    if not user:
        return jsonify({'error': 'Utilisateur non trouvé'}), 404
    
    livre = Livre.query.get(data['livre_id'])
    if not livre:
        return jsonify({'error': 'Livre non trouvé'}), 404
    
    if not livre.disponible:
        return jsonify({'error': 'Le livre n\'est pas disponible'}), 400
    
    reservation = Reservation(
        user_id=data['user_id'],
        livre_id=data['livre_id'],
        date_debut=data['date_debut'],
        date_fin=data['date_fin'],
        status='RESERVEE'
    )
    
    db.session.add(reservation)
    db.session.commit()
    
    return jsonify(reservation.to_dict()), 201

# SOAP-like Administrative API (simulated with REST)
@app.route('/api/admin/livres', methods=['POST'])
def admin_add_livre():
    data = request.get_json()
    
    # Validation de base
    required_fields = ['titre', 'auteur']
    for field in required_fields:
        if field not in data:
            return jsonify({'error': f'Le champ {field} est requis'}), 400
    
    livre = Livre(
        titre=data['titre'],
        auteur=data['auteur'],
        isbn=data.get('isbn'),
        description=data.get('description'),
        annee_publication=data.get('annee_publication'),
        disponible=True
    )
    
    db.session.add(livre)
    db.session.commit()
    
    return jsonify({
        'success': True,
        'message': 'Livre ajouté avec succès',
        'livre': livre.to_dict()
    })

@app.route('/api/admin/livres/<int:id>', methods=['PUT'])
def admin_update_livre(id):
    livre = Livre.query.get_or_404(id)
    data = request.get_json()
    
    if 'titre' in data:
        livre.titre = data['titre']
    if 'auteur' in data:
        livre.auteur = data['auteur']
    if 'isbn' in data:
        livre.isbn = data['isbn']
    if 'description' in data:
        livre.description = data['description']
    if 'annee_publication' in data:
        livre.annee_publication = data['annee_publication']
    
    db.session.commit()
    
    return jsonify({
        'success': True,
        'message': 'Livre modifié avec succès',
        'livre': livre.to_dict()
    })

@app.route('/api/admin/livres/<int:id>', methods=['DELETE'])
def admin_delete_livre(id):
    livre = Livre.query.get_or_404(id)
    
    # Vérifier s'il y a des réservations actives
    active_reservations = Reservation.query.filter_by(livre_id=id).filter(Reservation.status.in_(['RESERVEE', 'EMPRUNTEE'])).count()
    if active_reservations > 0:
        return jsonify({
            'success': False,
            'message': 'Impossible de supprimer un livre avec des réservations actives'
        }), 400
    
    db.session.delete(livre)
    db.session.commit()
    
    return jsonify({
        'success': True,
        'message': 'Livre supprimé avec succès'
    })

@app.route('/api/admin/prets', methods=['POST'])
def admin_preter_livre():
    data = request.get_json()
    
    if 'user_id' not in data or 'livre_id' not in data:
        return jsonify({'error': 'Les champs user_id et livre_id sont requis'}), 400
    
    user = User.query.get(data['user_id'])
    if not user:
        return jsonify({'error': 'Utilisateur non trouvé'}), 404
    
    livre = Livre.query.get(data['livre_id'])
    if not livre:
        return jsonify({'error': 'Livre non trouvé'}), 404
    
    if not livre.disponible:
        return jsonify({'error': 'Le livre n\'est pas disponible'}), 400
    
    # Chercher une réservation existante ou en créer une nouvelle
    reservation = Reservation.query.filter_by(user_id=data['user_id'], livre_id=data['livre_id'], status='RESERVEE').first()
    
    if not reservation:
        # Créer une nouvelle réservation
        from datetime import date, timedelta
        today = date.today()
        reservation = Reservation(
            user_id=data['user_id'],
            livre_id=data['livre_id'],
            date_debut=today,
            date_fin=today + timedelta(days=14),  # 2 semaines par défaut
            status='EMPRUNTEE'
        )
        db.session.add(reservation)
    else:
        # Mettre à jour la réservation existante
        reservation.status = 'EMPRUNTEE'
    
    # Marquer le livre comme indisponible
    livre.disponible = False
    
    db.session.commit()
    
    return jsonify({
        'success': True,
        'message': 'Livre prêté avec succès',
        'reservation': reservation.to_dict()
    })

@app.route('/api/admin/retours', methods=['POST'])
def admin_retourner_livre():
    data = request.get_json()
    
    if 'user_id' not in data or 'livre_id' not in data:
        return jsonify({'error': 'Les champs user_id et livre_id sont requis'}), 400
    
    user = User.query.get(data['user_id'])
    if not user:
        return jsonify({'error': 'Utilisateur non trouvé'}), 404
    
    livre = Livre.query.get(data['livre_id'])
    if not livre:
        return jsonify({'error': 'Livre non trouvé'}), 404
    
    # Chercher la réservation active
    reservation = Reservation.query.filter_by(
        user_id=data['user_id'], 
        livre_id=data['livre_id'], 
        status='EMPRUNTEE'
    ).first()
    
    if not reservation:
        return jsonify({'error': 'Aucun emprunt actif trouvé pour cet utilisateur et ce livre'}), 404
    
    # Mettre à jour la réservation
    from datetime import date
    reservation.status = 'RETOURNEE'
    reservation.date_retour = date.today()
    
    # Marquer le livre comme disponible
    livre.disponible = True
    
    db.session.commit()
    
    return jsonify({
        'success': True,
        'message': 'Livre retourné avec succès',
        'reservation': reservation.to_dict()
    })

# Route par défaut
@app.route('/')
def index():
    return '''
    <h1>API de Gestion de Bibliothèque</h1>
    <p>Système hybride de gestion de bibliothèque avec API REST pour l'accès public et API administrative pour les fonctions d'administration.</p>
    <h2>Endpoints API REST (publics)</h2>
    <ul>
        <li>GET /api/livres - Liste tous les livres</li>
        <li>GET /api/livres/{id} - Affiche les détails d'un livre</li>
        <li>GET /api/livres/disponibles - Liste les livres disponibles</li>
        <li>GET /api/reservations/{id} - Affiche les détails d'une réservation</li>
        <li>POST /api/reservations - Crée une nouvelle réservation</li>
    </ul>
    <h2>Endpoints API Administrative</h2>
    <ul>
        <li>POST /api/admin/livres - Ajoute un nouveau livre</li>
        <li>PUT /api/admin/livres/{id} - Modifie un livre existant</li>
        <li>DELETE /api/admin/livres/{id} - Supprime un livre</li>
        <li>POST /api/admin/prets - Prête un livre à un utilisateur</li>
        <li>POST /api/admin/retours - Enregistre le retour d'un livre</li>
    </ul>
    '''

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)