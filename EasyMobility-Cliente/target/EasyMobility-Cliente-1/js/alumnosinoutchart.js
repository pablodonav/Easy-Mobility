// objetos para contar el número de estudiantes que entraron y salieron por año
var entradas = {};
var salidas = {};

for (var i = 0; i < acuerdosEntrada.length; i++) {
    var acuerdo = acuerdosEntrada[i];
    var alumnoNombre = acuerdo.alumno.nombre;
    var cursoAcademico = acuerdo.cursoAcademico;

    if (!entradas[cursoAcademico]) {
        entradas[cursoAcademico] = 1;
    } else {
        entradas[cursoAcademico]++;
    }
}

for (var i = 0; i < acuerdosSalida.length; i++) {
    var acuerdo = acuerdosSalida[i];
    var alumnoNombre = acuerdo.alumno.nombre;
    var cursoAcademico = acuerdo.cursoAcademico;

    if (!salidas[cursoAcademico]) {
        salidas[cursoAcademico] = 1;
    } else {
        salidas[cursoAcademico]++;
    }
}

// Unimos los cursos académicos de entradas y salidas
var entradasKeys = Object.keys(entradas);
var salidasKeys = Object.keys(salidas);
var labels = entradasKeys.concat(salidasKeys.filter(item => !entradasKeys.includes(item)));

// arreglo de datos del gráfico
var data = {
    labels: labels, // los años serán las etiquetas del gráfico
    datasets: [
        {
            label: "Entradas",
            backgroundColor: "rgba(54, 162, 235, 0.5)",
            data: Object.values(entradas) // los valores serán los conteos de entradas
        },
        {
            label: "Salidas",
            backgroundColor: "rgba(255, 99, 132, 0.5)",
            data: Object.values(salidas) // los valores serán los conteos de salidas
        }
    ]
};

// opciones del gráfico
var options = {
    scales: {
        yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
    }
};

// creamos el gráfico
var ctx = document.getElementById('alumnosinoutchart').getContext('2d');
var alumnosinoutchart = new Chart(ctx, {
    type: 'bar',
    data: data,
    options: options
});
