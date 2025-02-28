package org.example;

public enum RequestTypes
{
    DELETE_ACCOUNT, // Cerere pentru ștergerea contului (făcută de regular/contributor pentru admini)
    ACTOR_ISSUE,    // Cerere în legătură cu datele privind un actor (făcută de regular/contributor pentru contributorul/adminul care a adăugat actorul respectiv în sistem)
    MOVIE_ISSUE,    // Cerere în legătură cu datele privind o producție (făcută de regular/contributor pentru contributorul/adminul care a adăugat producția respectivă în sistem)
    OTHERS          // Cereri care nu se încadrează în niciuna din cererile de mai sus (exemplu: data nașterii contului personal a fost completată greșit și necesită actualizare), destinate doar adminilor
}
