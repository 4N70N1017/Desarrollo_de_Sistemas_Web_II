// Script personalizado para Tienda Sara

document.addEventListener('DOMContentLoaded', function() {
    console.log('Tienda Sara cargada correctamente');

    // Funcionalidad para eliminar productos del carrito
    const botonesEliminar = document.querySelectorAll('.btn-danger');
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', function() {
            if(confirm('¿Deseas eliminar este producto del carrito?')) {
                this.closest('tr').remove();
            }
        });
    });

    // Actualizar cantidad en carrito
    const inputsCantidad = document.querySelectorAll('input[type="number"]');
    inputsCantidad.forEach(input => {
        input.addEventListener('change', function() {
            console.log('Cantidad actualizada: ' + this.value);
        });
    });
});