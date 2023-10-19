import React  from "react";
import { FormGroup, InputGroup } from "@blueprintjs/core";

const InputField = ({ value, label, name, placeholder, type, onChange }) => (
  <FormGroup>
    {label && <label htmlFor="input-field">{label}</label>}
    <InputGroup
      type={type}
      value={value}
      name={name}
      className="form-control"
      placeholder={placeholder}
      onChange={onChange}
    />
  </FormGroup>
);

export default InputField