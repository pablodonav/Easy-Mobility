var destinosFavoritosOut = {}; // objeto para almacenar el recuento de cada país

for (var i = 0; i < acuerdosSalida.length; i++) {
    var paisOut = acuerdosSalida[i].estudioDestino.centro.localizacion.pais;
    if (destinosFavoritosOut[paisOut]) {
        destinosFavoritosOut[paisOut]++;
    } else {
        destinosFavoritosOut[paisOut] = 1;
    }
}

var destinosFavoritosOrdenadosOut = Object.entries(destinosFavoritosOut)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 6);

var paisesOut = destinosFavoritosOrdenadosOut.map((destino) => destino[0]);
var cantidadesOut = destinosFavoritosOrdenadosOut.map((destino) => destino[1]);

var ctx = document.getElementById('paisesfavoritosOutchart').getContext('2d');

if (Object.keys(destinosFavoritosOut).length > 0) {
    // si hay datos para el gráfico
    var chart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: paisesOut,
            datasets: [{
                    data: cantidadesOut,
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