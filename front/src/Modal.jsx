import React from 'react';
import './Modal.css';
import MeiliApp from './Meilisearch';

const Modal = ({ onClose }) => {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <MeiliApp></MeiliApp>
      </div>
    </div>
  );
};

export default Modal;
