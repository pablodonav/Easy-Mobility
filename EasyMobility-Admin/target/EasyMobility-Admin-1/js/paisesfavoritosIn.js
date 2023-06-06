var destinosFavoritosIn = {}; // objeto para almacenar el recuento de cada país

for (var j = 0; j < acuerdosEntrada.length; j++) {
    var paisIn = acuerdosEntrada[j].estudioOrigen.centro.localizacion.pais;
    if (destinosFavoritosIn[paisIn]) {
        destinosFavoritosIn[paisIn]++;
    } else {
        destinosFavoritosIn[paisIn] = 1;
    }
}

var destinosFavoritosOrdenadosIn = Object.entries(destinosFavoritosIn)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 6);

var paisesIn = destinosFavoritosOrdenadosIn.map((destino) => destino[0]);
var cantidadesIn = destinosFavoritosOrdenadosIn.map((destino) => destino[1]);

var ctx = document.getElementById('paisesfavoritosInchart').getContext('2d');

if (Object.keys(destinosFavoritosIn).length > 0) {
    // si hay datos para el gráfico
    var chart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: paisesIn,
            datasets: [{
                    data: cantidadesIn,
                    backgroundColor: [
                        '#FF6384',
                        '#36A2EB',
                        '#FFCE56',
                        '#4BC0C0',
                        '#9966FF',
                        '#FF9F40'
                    ]
                }]
        },
        options: {
            responsive: true
        }
    });
} else {
    // si no hay datos para el gráfico
    var chart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Sin datos'],
            datasets: [{
                    data: [1],
                    backgroundColor: ['#cccccc']
                }]
        },
        options: {
            responsive: true,
            plugins: {
                datalabels: {
                    display: true,
                    color: '#000000',
                    font: {
                        weight: 'bold'
                    },
                    formatter: function (value, context) {
                        return '';
                    }
                }
            }
        }
    });
}