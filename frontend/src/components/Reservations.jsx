// src/components/Reservations.jsx
import React, { useState } from "react";
import "../styles/main.scss";

const Reservations = () => {
  const [reservations, setReservations] = useState([]);
  const [reservationName, setReservationName] = useState("");
  const [tableId, setTableId] = useState("");
  const [reservationDate, setReservationDate] = useState("");
  const [editingIndex, setEditingIndex] = useState(null);

  const handleAddReservation = (e) => {
    e.preventDefault();

    if (!reservationName || !tableId || !reservationDate) {
      alert("Please fill out all fields.");
      return;
    }

    const newReservation = {
      reservationName,
      tableId,
      reservationDate,
    };

    if (editingIndex !== null) {
      // Update existing
      const updated = [...reservations];
      updated[editingIndex] = newReservation;
      setReservations(updated);
      setEditingIndex(null);
    } else {
      // Add new
      setReservations([...reservations, newReservation]);
    }

    // Reset
    setReservationName("");
    setTableId("");
    setReservationDate("");
  };

  const handleEditReservation = (index) => {
    const r = reservations[index];
    setReservationName(r.reservationName);
    setTableId(r.tableId);
    setReservationDate(r.reservationDate);
    setEditingIndex(index);
  };

  const handleDeleteReservation = (index) => {
    const updated = reservations.filter((_, i) => i !== index);
    setReservations(updated);
  };

  return (
    <div className="reservations-container">
      <h1>Reservations</h1>

      <form className="reservation-form" onSubmit={handleAddReservation}>
        <div className="form-group">
          <label htmlFor="reservationName">Reservation Name</label>
          <input
            type="text"
            id="reservationName"
            value={reservationName}
            onChange={(e) => setReservationName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="tableId">Table ID</label>
          <input
            type="text"
            id="tableId"
            value={tableId}
            onChange={(e) => setTableId(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="reservationDate">Reservation Date</label>
          <input
            type="date"
            id="reservationDate"
            value={reservationDate}
            onChange={(e) => setReservationDate(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="submit-button">
          {editingIndex !== null ? "Update Reservation" : "Add Reservation"}
        </button>
      </form>

      <table className="reservation-table">
        <thead>
          <tr>
            <th>Reservation Name</th>
            <th>Table ID</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {reservations.map((res, index) => (
            <tr key={index}>
              <td>{res.reservationName}</td>
              <td>{res.tableId}</td>
              <td>{res.reservationDate}</td>
              <td>
                <button onClick={() => handleEditReservation(index)}>Edit</button>
                <button onClick={() => handleDeleteReservation(index)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Reservations;
