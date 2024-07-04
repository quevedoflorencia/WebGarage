document.getElementById('calificacionForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const calificacion = formData.get('calificacion');
    const comentario = formData.get('comentario');

    const data = {
        calificacion: calificacion,
        comentario: comentario,
        // Agrega otros campos necesarios, como userId y garageId
    };

    fetch('/api/calificaciones', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        alert('Calificación enviada exitosamente!');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al enviar tu calificación.');
    });
});
