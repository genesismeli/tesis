import React, { Component } from 'react';
import './createClinicalRecord.css';
import Message from '../Message.js';


class CreateClinicalRecord extends Component {
  constructor() {
      super();
      this.state = {
        date: '',
        patient: '',
        showPhysicalExams: false,
        showMedications: false,
        showDiagnoses: false,
        showMessage: false,
        showMessageDiagnosis: false,
        physicalExams: [
          {

          },
        ],
        medications: [
          {

          },
        ],
        diagnoses: [
          {

          },
        ],
        isModalOpen: false,
      };


  }

  componentDidMount() {
      // Obtén el ID del paciente de la URL o de donde sea que lo hayas pasado
      const params = new URLSearchParams(window.location.search);
      const patientId = params.get('patientId');

      // Actualiza el estado con el ID del paciente
      this.setState({ patient: patientId });


  // Realiza una solicitud al backend para obtener la información completa del paciente
      fetch(`http://localhost:8081/patient/${patientId}`)
        .then((response) => response.json())
        .then((patientData) => {
          this.setState({ patient: patientData });
        })
        .catch((error) => console.error('Error al obtener la información del paciente:', error));
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

handleAddMedication = () => {
  const lastMedication = this.state.medications[this.state.medications.length - 1];

  // Verifica si al menos un campo del último elemento está lleno antes de agregar uno nuevo
  if (lastMedication && Object.values(lastMedication).some(value => value !== '')) {
    this.setState((prevState) => ({
      medications: [
        ...prevState.medications,
        {
          medicationName: '',
          concentration: '',
          presentation: '',
          tradeName: '',
        },
      ],
      medicationError: '', // Reinicia el mensaje de error
    }));
  } else {
        this.setState({ medicationError: 'Complete al menos un dato del medicamento anterior.' });
  }
};

handleAddDiagnosis = () => {
  const lastDiagnosis = this.state.diagnoses[this.state.diagnoses.length - 1];

  // Verifica si al menos un campo del último elemento está lleno antes de agregar uno nuevo
  if (lastDiagnosis && Object.values(lastDiagnosis).some(value => value !== '')) {
    this.setState((prevState) => ({
      diagnoses: [
        ...prevState.diagnoses,
        {
          code: '',
          description: '',
          status: '',
          notes: '',

        },
      ],
      diagnosisError: '',
    }));
  } else {
        this.setState({ diagnosisError: 'Complete al menos un dato del diagnóstico anterior.' });
  }
};


handlePhysicalExamsChange = (event, index) => {
  const { name, value } = event.target;
  const numericValue = isNaN(value) ? value : parseFloat(value);

  this.setState((prevState) => {
    const updatedPhysicalExams = [...prevState.physicalExams];
    updatedPhysicalExams[index] = {
      ...updatedPhysicalExams[index],
      [name]: numericValue !== undefined ? numericValue : null,
    };
    return { physicalExams: updatedPhysicalExams };
  });
};


  handleMedicationsChange = (event, index) => {
    const { name, value } = event.target;

    this.setState((prevState) => {
      const updatedMedications = [...prevState.medications];
      updatedMedications[index] = {
        ...updatedMedications[index],
        [name]: value,
      };
      return { medications: updatedMedications };
    });
  };



 handleDiagnosesChange = (event, index) => {
    const { name, value } = event.target;

    this.setState((prevState) => {
      const updatedDiagnoses = [...prevState.diagnoses];
      updatedDiagnoses[index] = {
        ...updatedDiagnoses[index],
        [name]: value,
      };
      return { diagnoses: updatedDiagnoses };
    });
  };



  handleCreateClinicalRecord = (event) => {
    event.preventDefault();

    // Recopila los datos del estado
    const { date, patient, physicalExams, medications, diagnoses,showPhysicalExams, showMedications, showDiagnoses } = this.state;
    if (!date) {
        // Puedes establecer la fecha actual aquí si es necesario
        this.setState({ date: new Date() });
        return;
    }
    const isAnySectionFilled = [physicalExams, medications, diagnoses].some(section => {
        if (Array.isArray(section)) {
          return section.some(entry => Object.values(entry).some(value => value !== ''));
        } else {
          return section !== null && section !== undefined && section !== '';
        }
      });

      if (!isAnySectionFilled) {
        this.setState({ showMessage: true });
        return;
      }
    // Validación: Verifica si todas las secciones están vacías
      if (!date && !physicalExams.length && !medications.length && !diagnoses.length) {
        this.setState({ showMessage: true });
        return;
      }

      // Determina la sección activa
      let activeSectionData = null;
      if (showPhysicalExams) {
        activeSectionData = { physicalExams };
      } else if (showMedications) {
        activeSectionData = { medications };
      } else if (showDiagnoses) {
        activeSectionData = { diagnoses };
      }

      // Valida la sección activa
      if (!activeSectionData) {
        console.error('Seleccione al menos una sección para completar.');
         this.setState({ showMessage: true });
        return;
    }

    // Elimina las secciones que están completamente vacías
      const nonEmptySections = Object.fromEntries(
        Object.entries({ physicalExams, medications, diagnoses }).filter(([key, value]) => {
          if (Array.isArray(value)) {
            // Filtra los elementos null en la matriz
            const filteredArray = value.filter(item => item !== null);
            // Verifica si la matriz contiene al menos un objeto con datos
            return filteredArray.length > 0 && filteredArray.some(obj => Object.values(obj).some(val => val !== ''));
          } else {
            // Verifica si el valor no está vacío
            return value !== null && value !== undefined && value !== '';
          }
        })
      );

      // Agregamos las propiedades show* al objeto nonEmptySections
      nonEmptySections.showPhysicalExams = showPhysicalExams;
      nonEmptySections.showMedications = showMedications;
      nonEmptySections.showDiagnoses = showDiagnoses;

      // Construye el objeto con la información de la ficha clínica
      const clinicalRecordData = {
        date,
        patient,
        ...nonEmptySections,
      };


    // Realiza la solicitud al backend
    fetch('http://localhost:8081/clinical/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(clinicalRecordData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al crear la ficha clínica');
        }
        return response.json();
      })
      .then((data) => {
        console.log('Ficha clínica creada con éxito:', data);

      const params = new URLSearchParams(window.location.search);
      const patientId = params.get('patientId');
        window.location.href = `/clinical/patient/${patientId}`;

        // Aquí puedes manejar la respuesta del servidor o redirigir al usuario a otra página
      })
      .catch((error) => {
        console.error('Error al crear la ficha clínica:', error);
        // Imprime el cuerpo de la respuesta si está disponible
              if (error.response && error.response.text) {
                error.response.text().then((text) => {
                  console.error('Cuerpo de la respuesta:', text);
                });
              }

      });
  };

  formatBirthdate(rawBirthdate) {
    const birthdate = new Date(rawBirthdate);
    const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
    return birthdate.toLocaleDateString(undefined, options);
  }


  handleToggleFields = (field) => {
    this.setState((prevState) => ({
      [field]: !prevState[field],
    }));

  const otherFields = ['showPhysicalExams', 'showMedications', 'showDiagnoses'];
    otherFields.forEach((otherField) => {
      if (otherField !== field) {
        this.setState({ [otherField]: false });
      }
    });
  };

 renderPhysicalExamsFields() {
     return this.state.physicalExams.map((exam, index) => (
       <div key={index}>
         <h3 className="form-section-title">Examen Físico {index + 1}</h3>
         {/* Campos del examen físico */}
         <div className="form-section">
         <label className="form-label">
           F. Cardíaca:
           <input
             type="text"
             name="heartRate"
             value={exam.heartRate}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           F. Respiratoria:
           <input
             type="text"
             name="respiratoryRate"
             value={exam.respiratoryRate}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           SpO2:
           <input
             type="text"
             name="oxygenSaturation"
             value={exam.oxygenSaturation}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           P. Sistólica:
           <input
             type="text"
             name="systolicPressure"
             value={exam.systolicPressure}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           P. Diastólica:
           <input
             type="text"
             name="diastolicPressure"
             value={exam.diastolicPressure}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           LPM:
           <input
             type="text"
             name="beatsPerMinute"
             value={exam.beatsPerMinute}
             onChange={(e) => this.handlePhysicalExamsChange(e, index)}
             className="form-input"
           />
         </label>
          <label className="form-label">
            Glucosa:
            <input
              type="text"
              name="glucose"
              value={exam.glucose}
              onChange={(e) => this.handlePhysicalExamsChange(e, index)}
              className="form-input"
            />
          </label>
         ></div>
       </div>
     ));
   }

   renderMedicationsFields() {
     return this.state.medications.map((medication, index) => (
       <div key={index}>
         <h3 className="form-section-title">Medicamento {index + 1}</h3>
         <div className="form-section">
         <label className="form-label">
           Nombre del Medicamento:
           <input
             type="text"
             name="medicationName"
             value={medication.medicationName}
             onChange={(e) => this.handleMedicationsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           Concentración del Medicamento:
           <input
             type="text"
             name="concentration"
             value={medication.concentration}
             onChange={(e) => this.handleMedicationsChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           Presentación del Medicamento:
           <input
             type="text"
             name="presentation"
             value={medication.presentation}
             onChange={(e) => this.handleMedicationsChange(e, index)}
             className="form-input"
           />
         </label>
          <label className="form-label">
            Nombre Comercial del Medicamento:
            <input
              type="text"
              name="tradeName"
              value={medication.tradeName}
              onChange={(e) => this.handleMedicationsChange(e, index)}
              className="form-input"
            />
          </label>
          </div>
       </div>
     ));
   }

   renderDiagnosesFields() {
     return this.state.diagnoses.map((diagnosis, index) => (
       <div key={index}>
         <h3 className="form-section-title">Diagnóstico {index + 1}</h3>
         {/* Campos de los diagnósticos */}
         <div className="form-section">
         <label className="form-label">
           Código del Diagnóstico:
           <input
             type="text"
             name="code"
             value={diagnosis.code}
             onChange={(e) => this.handleDiagnosesChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           Descripción del Diagnóstico:
           <input
             type="text"
             name="description"
             value={diagnosis.description}
             onChange={(e) => this.handleDiagnosesChange(e, index)}
             className="form-input"
           />
         </label>
         <label className="form-label">
           Estado del Diagnóstico:
           <select
             name="status"
             value={diagnosis.status}
             onChange={(e) => this.handleDiagnosesChange(e, index)}
             className="form-input"
           >
             <option value="">Seleccionar Estado</option>
             <option value="ACTIVO">Activo</option>
             <option value="RECUPERADO">Recuperado</option>
             <option value="RECURRENCIA">Recurrencia</option>
             <option value="RECAIDA">Recaída</option>
             <option value="INACTIVO">Inactivo</option>
             <option value="REMISION">Remisión</option>
             <option value="RESUELTO">Resuelto</option>
           </select>
         </label>

         <label className="form-label">
           Notas del Diagnóstico:
           <input
             type="text"
             name="notes"
             value={diagnosis.notes}
             onChange={(e) => this.handleDiagnosesChange(e, index)}
             className="form-input"
           />
         </label>
         </div>
       </div>
     ));
   }

   render() {
     return (
       <div className="clinical-record-container">
         {this.state.showMessage && (
           <Message
             text="Debe completar al menos una sección"
             onClose={() => this.setState({ showMessage: false })}
             />
             )}
         {this.state.showMessageDiagnosis && (
            <Message
              text="Debe completar el diagnostico anterior"
              onClose={() => this.setState({ showMessageDiagnosis: false })}
              />
              )}
         <div className="left-container">
         <h3 className="form-section-title"> Datos del paciente </h3>
             <p> <strong>Paciente:</strong> {this.state.patient.name} {this.state.patient.lastName} </p>
             <p> <strong>Fecha de nacimiento:</strong> {this.formatBirthdate(this.state.patient.birthdate)} </p>
             <p> <strong>Teléfono:</strong> {this.state.patient.phone} </p>
             <p> <strong>Email:</strong> {this.state.patient.email} </p>
             <p> <strong>DNI:</strong> {this.state.patient.dni} </p>
         </div>

         <div className="right-container">
         <form className="clinical-record-form" onSubmit={this.handleCreateClinicalRecord}>
         <h2 className="form-title">Crear Nueva Ficha Clínica</h2>

           <h3
             className="form-section-title"
             onClick={() => this.handleToggleFields('showPhysicalExams')}
           >
             Exámenes Físicos
             {this.state.showPhysicalExams ? ' ▼' : ' ▶'}
           </h3>
           {this.state.showPhysicalExams && this.renderPhysicalExamsFields()}

           <h3
             className="form-section-title"
             onClick={() => this.handleToggleFields('showMedications')}
           >
             Medicamentos
             {this.state.showMedications ? ' ▼' : ' ▶'}
           </h3>
           {this.state.showMedications && this.renderMedicationsFields()}
           <button
               type="button"
               onClick={() => this.handleAddMedication()}
               className={`form-add-button ${this.state.showMedications ? 'show-buttons' : 'hide-buttons'}`}
            >
               Agregar Medicamento
            </button>

           <h3
             className="form-section-title"
             onClick={() => this.handleToggleFields('showDiagnoses')}
           >
             Diagnósticos
             {this.state.showDiagnoses ? ' ▼' : ' ▶'}
           </h3>
           {this.state.showDiagnoses && this.renderDiagnosesFields()}
            <button
               type="button"
               onClick={() => this.handleAddDiagnosis()}
               className={`form-add-button ${this.state.showDiagnoses ? 'show-buttons' : 'hide-buttons'}`}
            >
               Agregar Diagnóstico
            </button>

           <button type="submit" className="form-submit-button">
             Guardar Ficha Clínica
           </button>
         </form>
         </div>
       </div>
     );
   }
 }

 export default CreateClinicalRecord;