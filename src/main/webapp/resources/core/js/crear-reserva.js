const errorHour = document.getElementById("hourError") //from
const errorHourUntil = document.getElementById("hourErrorUntil") //until
const containerHourPicker = document.getElementById("container_hour_picker");
const timepicker_until_container = document.getElementById("timepicker_until_container");
const containerGoReserva = document.getElementById("container-go-reserva");
const containerDatePickerFrom = document.getElementById("container_date_picker_from")
let tipo_vehiculo = document.getElementById("tipo_vehiculo");
let datePickerFrom = '';
let timePickerFrom = null;
let timePickerUntil = null;
let filedHours = [];


flatpickr("#datepicker_from", {
    dateFormat: "Y-m-d",
    altInput: true,
    altFormat: "F j, Y",
    minDate: "today",
    onChange: function (selectedDates, dateStr, instance) {
        resetForm()
        // format "yyyy-mm-dd"
        const selectedDate = selectedDates[0];
        const formattedDate = selectedDate.toISOString().split('T')[0];
        datePickerFrom = formattedDate;
        getFiledHours(formattedDate);
    }
});


function resetForm() {
    filedHours = []
    datePickerFrom = null;
    datePickerUntil = null;
    errorHour.innerText = "";
    errorHourUntil.innerText = "";

    document.getElementById("timepicker_from").value = '';
    document.getElementById("timepicker_until").value = '';
    containerHourPicker.classList.add("d-none");
    timepicker_until_container.classList.add("d-none");
    containerGoReserva.classList.add("d-none");
}

function getFiledHours(selectedDate) {
    let data = JSON.stringify({selectedDate: selectedDate.toString(), garageTipoVehiculoId: parseInt(tipo_vehiculo.value), garageId: idGarage})
    fetch('http://localhost:8080/getAvailableHours', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: data
    })
        .then(response => response.json())
        .then(data => {
            //const dataDummy = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19, 20,21,22,23];
            const dataDummy = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 16, 17, 18, 19, 20, 21, 22, 23];

            filedHours = data;

            if (filedHours.length !== 0) {
                if (filedHours.length === totalHours) {
                    errorHour.innerText = "Todos los horarios están ocupados, por favor selecciona otra fecha."
                }

                // Obtener el elemento select
                let selectElement = document.getElementById("timepicker_from");
                filedHours.forEach(hour => {
                    disabledFiledHour(hour, selectElement)
                });
            }
        })
        .catch(error => console.log('Error fetching data:', error));
    containerHourPicker.classList.remove("d-none");
}

function disabledFiledHour(hour, selectElement) {
    // Convertir la hora ocupada a formato 'HH:mm'
    let formattedHour = formatHour(hour)
    console.log("formatedd", formattedHour)
    //obtener el option para luego deshabilitarlo
    let option = selectElement.querySelector(`option[value='${hour.toString()}'], option[value='${formattedHour}']`);
    if (option) {
        option.disabled = true;
    }
}

// al seleccionar el horario de inicio
document.getElementById("timepicker_from").addEventListener("change", function () {
    timepicker_until_container.classList.remove("d-none")
    containerGoReserva.classList.add("d-none")
    document.getElementById("timepicker_until").value = '';
    const selectedHourFrom = this.value;
    timePickerFrom = parseInt(this.value, 10);
    // deshabilita la hora para el horario Hasta para que no se elija una opcion que este antes del horario FROM
    timePickerFromNotAllowOnPickerUntil = formatHour(this.value);
    const timepickerUntil = document.getElementById("timepicker_until");
    const options = timepickerUntil.options;

    for (let i = 0; i < options.length; i++) {
        const hour = parseInt(options[i].value, 10);
        if (hour <= timePickerFrom || filedHours.includes(hour.toString())) {
            options[i].disabled = true;
        } else {
            options[i].disabled = false;
        }
    }
});

//al seleccionar el horario de finalizacion
document.getElementById("timepicker_until").addEventListener("change", function () {
    timePickerUntil = parseInt(this.value, 10);
    if (tipo_vehiculo.value != null && tipo_vehiculo.value != undefined && tipo_vehiculo.value != '') {
        containerGoReserva.classList.remove("d-none")
    }
})

//ir a la reserva
document.getElementById("goToReservation").addEventListener("click", function () {
    console.log("time from", timePickerFrom) //time from 10
    console.log("time until", timePickerUntil) //time until 13
    console.log("date", datePickerFrom) // date 2024-05-05
    //TODO ir al controlador y seguir el flujo y enviar estos datos..
})

function formatHour(hour) {
    // Si la hora tiene un solo dígito, agrega un '0' al inicio,y despues  ':00'
    // Si no,  agrega ':00' al final
    return hour < 10 ? '0' + hour + ':00' : hour + ':00';
}

// Iterar sobre cada elemento ".selection"
document.querySelectorAll(".selection_tipo_vehiculo .selection").forEach(function (element) {
    var id = element.getAttribute("id")
    console.log(id);
    if (element.classList.contains('disabled')) {
        element.addEventListener("click", function (event) {
            event.preventDefault();
        });
    } else {
        // Si el vehículo está en garageTipoVehiculo, agregar evento click para selección
        element.addEventListener("click", function () {
            // Primero, desmarcar todos los elementos
            document.querySelectorAll(".selection_tipo_vehiculo .selection").forEach(function (el) {
                el.classList.remove("active");
            });

            // Marcar el elemento actual
            element.classList.add("active");
            tipo_vehiculo.value = element.id

            // TODO: resetear el input fecha -
            resetForm();
            containerDatePickerFrom.classList.remove("d-none")

            //TODO: revisar donde ejecutar esta logica.
            // verificar si se seleccionó todos los campos y en caso de que sí mostrar el btn para continuar:
            if (datePickerFrom != null && timePickerFrom != null && timePickerUntil != null) {
                containerGoReserva.classList.remove("d-none");
            }
        });
    }
});

let map = L.map('map').setView([latitudGarage, longitudGarage], 15);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);

L.marker([latitudGarage, longitudGarage]).addTo(map);