import React, { Component } from 'react';
import './medicList.css';

import EditMedicForm from './EditMedicForm';
import DeleteMedicForm from './DeleteMedicForm';
import TrashIcon from '../assets/icons/trash-icon.svg'; // Ruta al archivo SVG del tacho de basura
import PencilIcon from '../assets/icons/pencil-icon.svg';

class MedicList extends Component {
  constructor() {
    super();
    this.state = {
      medics: [],
      selectedMedic: null,
      selectedMedicDelete : null,
      isDeleteModalOpen: false,
    };
  }


  componentDidMount() {
    // Aquí puedes realizar una solicitud HTTP para obtener la lista de médicos
    // Puedes usar fetch, axios u otra biblioteca de HTTP aquí
    // Actualiza el estado con la lista de médicos obtenida del servidor
    // Ejemplo de solicitud ficticia:
    fetch('http://localhost:8081/medic/all')
      .then((response) => response.json())
      .then((data) => {
        this.setState({ medics: data }); // Actualiza el estado con la lista de médicos
      })
      .catch((error) => {
        console.error('Error al obtener la lista de médicos:', error);
      });
  }

  updateMedicList = () => {
    // Realiza la solicitud HTTP para obtener la lista de médicos y actualiza el estado
    fetch('http://localhost:8081/medic/all')
      .then((response) => response.json())
      .then((data) => {
        this.setState({ medics: data }); // Actualiza el estado con la lista de médicos
      })
      .catch((error) => {
        console.error('Error al obtener la lista de médicos:', error);
      });
  };



  handleCreateMedicClick = () => {
    // Utiliza window.location.href para redirigir a la página de creación de médicos
    window.location.href = '/medic/create';
  };

   handleEditMedicClick = (medic) => {
       this.setState({ selectedMedic: medic });
       this.updateMedicList();
   };

    handleDeleteMedicClick = (medic) => {
      this.setState({ selectedMedicDelete: medic });
      this.updateMedicList();

    };


  render() {
      const { medics, selectedMedic, selectedMedicDelete  } = this.state;
      return (
        <div className="medic-list-container">
          <h2>Lista de Médicos</h2>
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Número de Registro</th>
                <th>Especialidad</th>
                <th>Correo Electrónico</th>
                <th>Acciones</th> {/* Nueva columna para los botones */}
              </tr>
            </thead>
            <tbody>
              {medics.map((medic) => (
                <tr key={medic.id}>
                  <td>{medic.name}</td>
                  <td>{medic.lastName}</td>
                  <td>{medic.registrationNumber}</td>
                  <td>{medic.speciality}</td>
                  <td>{medic.email}</td>
                  <td>
                      <button onClick={() => this.handleEditMedicClick(medic)}>
                          <img src={PencilIcon} alt="Editar" width="20" height="20" />
                      </button>
                     <button onClick={() => this.handleDeleteMedicClick(medic)}>
                        <img src={TrashIcon} alt="Eliminar" width="20" height="20" />
                     </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {/* Utiliza handleCreateMedicClick al hacer clic en el botón */}
          <button className="create-medic-button" onClick={this.handleCreateMedicClick}>Crear Médico</button>
           {selectedMedic && <EditMedicForm medic={selectedMedic} onClose={() => this.setState({ selectedMedic: null })} />}
          {selectedMedicDelete && (
                    <DeleteMedicForm
                      medic={selectedMedicDelete}
                      onCancel={() => this.setState({ selectedMedicDelete: null })}
                      onConfirm={this.handleConfirmDelete}/>)}
        </div>
      );
    }
  }

  export default MedicList;