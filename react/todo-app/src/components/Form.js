import React from "react";

export default function Form({ value, setValue, handleSubmit }) {
  const handleChange = (e) => {
    setValue(e.target.value);
  };

  return (
    <div>
      <form style={{ display: "flex" }} onSubmit={handleSubmit}>
        <input
          type={"text"}
          name={"value"}
          style={{ flex: "10", padding: "5px" }}
          placeholder={"해야 할 일을 입력하세요."}
          value={value}
          onChange={handleChange}
        />
        <input
          type={"submit"}
          value={"입력"}
          className={"btn"}
          style={{ flex: "1" }}
        />
      </form>
    </div>
  );
}
