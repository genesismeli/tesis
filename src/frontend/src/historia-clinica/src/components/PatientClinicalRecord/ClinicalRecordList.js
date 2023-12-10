import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './clinicalRecord.css';
import ViewIcon from '../../assets/icons/view-icon.svg';
import DownloadIcon from '../../assets/icons/download-icon.svg';
import SigIcon from '../../assets/icons/flecha-adel-icon.svg';
import AntIcon from '../../assets/icons/flecha-ant-icon.svg';
const ClinicalRecordList = () => {
  const [clinicalRecords, setClinicalRecords] = useState([]);
  const [selectedRecord, setSelectedRecord] = useState(null); // Nuevo estado para almacenar la ficha clínica seleccionada
  const [isModalOpen, setIsModalOpen] = useState(false); // Estado para controlar si el modal está abierto
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const { patientId } = useParams();

  const traducirEstado = (status) => {
    switch (status) {
      case 'ACTIVE':
        return 'Activo';
      case 'INACTIVE':
        return 'Inactivo';
      case 'RECOVERED':
        return 'Recuperado';
      case 'RELAPSE':
        return 'Recaída';
      case 'RECURRENCE':
        return 'Recurrente';
      case 'REMISSION':
        return 'Remisión';
      case 'RESOLVED':
        return 'Resuelto'
      default:
        return status;
    }
  };


  useEffect(() => {
    // Llamada a la API para obtener las fichas clínicas del paciente
    fetch(`http://localhost:8081/clinical/patient/${patientId}?page=${currentPage}`)
      .then((response) => response.json())
      .then((data) => {
        setClinicalRecords(data.content);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.error('Error al obtener las fichas clínicas:', error);
      });
  }, [patientId, currentPage]);

    const handleNextPage = () => {
      if (currentPage < totalPages - 1) {
          setCurrentPage((prevPage) => prevPage + 1);
      }
    };

    const handlePrevPage = () => {
      if (currentPage > 0) {
          setCurrentPage((prevPage) => prevPage - 1);
      }
    };




    // Código en el componente anterior que muestra la lista de fichas clínicas
    const handleCreateClinicalRecordClick = (patientId) => {
      // Redirige al usuario a la página de creación de fichas clínicas
      window.location.href = `/clinical/add?patientId=${patientId}`;
    };


  const viewClinicalRecord = (recordId) => {
    // Llamada a la API para obtener la ficha clínica por ID
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8081/clinical/${recordId}`, {
        headers: {
          Authorization: `Bearer ${token}`, // Incluye el token en el encabezado de autorización
        },
      })
      .then((response) => response.json())
      .then((data) => {
        setSelectedRecord(data); // Almacena la ficha clínica seleccionada
        setIsModalOpen(true); // Abre el modal
      })
      .catch((error) => {
        console.error('Error al obtener la ficha clínica por ID:', error);
      });
  };
const downloadClinicalRecord = (recordId) => {
  // Llamada al nuevo endpoint para obtener el PDF
  const token = localStorage.getItem('token');
  fetch(`http://localhost:8081/pdf/clinical-record/${recordId}/pdf`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then((response) => response.blob())
    .then((blob) => {
      // Crear un objeto URL para el blob
      const url = window.URL.createObjectURL(new Blob([blob]));

      // Crear un enlace invisible y hacer clic en él para iniciar la descarga
      const a = document.createElement('a');
      a.href = url;
      a.download = 'ficha_clinica.pdf';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

      // Revocar el objeto URL para liberar recursos
      window.URL.revokeObjectURL(url);
    })
    .catch((error) => {
      console.error('Error al obtener el PDF:', error);
    });
};

  const closeViewModal = () => {
    setIsModalOpen(false); // Cierra el modal
  };

  return (
    <div className= "clinical-record-list-container">
      <div>
      <h2>Lista de Fichas Clínicas</h2>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Fecha de Creación</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {clinicalRecords.map((record) => (
            <tr key={record.id}>
              <td>Ficha Clínica</td>
              <td>{`${new Date(record.date).toLocaleDateString()} ${new Date(record.date).toLocaleTimeString()}`}</td>
              <td>
                <button onClick={() => viewClinicalRecord(record.id)}>
                  <img src={ViewIcon} alt="Ver Ficha" width="20" height="20" />
                </button>
                <button onClick={() => downloadClinicalRecord(record.id)}>
                  <img src={DownloadIcon} alt="Descargar Ficha" width="20" height="20" />
                </button>
              </td>
            </tr>
          ))}

        </tbody>
      </table>
        <div className="pagination-buttons">
        <button onClick={handlePrevPage} disabled={currentPage === 0}>
          <img src={AntIcon} alt="Anterior" width="20" height="20" />
        </button>
        <span>Página {currentPage + 1} de {totalPages}</span>
        <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
        <img src={SigIcon} alt="Siguiente" width="20" height="20" />
        </button>
        </div>
      </div>
      <div>
      <button className="create-record-button" onClick={() => handleCreateClinicalRecordClick(patientId)}>Crear Ficha </button>
      </div>

      {/* Modal para mostrar la ficha clínica */}
      {isModalOpen && (
        <div className="modal active">
          <button className="close-button" onClick={closeViewModal}>Cerrar</button>
          <h2 className="title">Ficha Clínica</h2>
          {/* Renderiza los detalles de la ficha clínica */}
          {selectedRecord && (
            <div>
              <h4 className="title">Exámenes Físicos</h4>
              <table>
                <thead>
                  <tr>
                    <th>F. Cardíaca</th>
                    <th>Saturación de Oxígeno</th>
                    <th>F. Respiratoria</th>
                    <th>P. Sistólica</th>
                    <th>P. Diastólica</th>
                    <th>LPM</th>
                    <th>Glucosa</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.physicalExams.map((exam) => (
                    <tr key={exam.id}>
                      <td>{exam.heartRate}</td>
                      <td>{exam.oxygenSaturation}</td>
                      <td>{exam.respiratoryRate}</td>
                      <td>{exam.systolicPressure}</td>
                      <td>{exam.diastolicPressure}</td>
                      <td>{exam.beatsPerMinute}</td>
                      <td>{exam.glucose}</td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <h4 className="title">Medicamentos</h4>
              <table>
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>Concentración</th>
                    <th>Presentación</th>
                     <th>Nombre Comercial</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.medications.map((medication) => (
                    <tr key={medication.id}>
                      <td>{medication.medicationName}</td>
                      <td>{medication.concentration}</td>
                      <td>{medication.presentation}</td>
                      <td>{medication.tradeName}</td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <h4 className="title">Diagnósticos</h4>
              <table>
                <thead>
                  <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.diagnoses.map((diagnosis) => (
                    <tr key={diagnosis.id}>
                      <td>{diagnosis.code}</td>
                      <td>{diagnosis.description}</td>
                      <td>{traducirEstado(diagnosis.status)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

        </div>
      )}
    </div>
  );
};

export default ClinicalRecordList;


