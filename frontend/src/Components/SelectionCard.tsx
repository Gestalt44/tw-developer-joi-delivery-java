export function SelectionCard({ data, mapping, dynamicComponent }) {
  if (data.length === 0) {
    return <>No Data Found</>;
  }

  return (
    <>
      {data.map((item, index) => {
        return (
          <div key={item.id || index}>
            {SelectionElement(item, mapping)}
            {dynamicComponent(item)}
          </div>
        );
      })}
    </>
  );
}

function SelectionElement(item, mapping) {
  return Object.keys(mapping).map((key) => {
    if (key in item) {
      return (
        <span>
          <label>{mapping[key]}</label>
          <h4>{item[key]}</h4>
        </span>
      );
    }
  });
}
