import JoiBranding from '../assets/joi_delivery_branding.svg';
import LandingAbstractArt from '../assets/landing_abstract_art.svg';
import { SelectionCard } from '../Components/SelectionCard.tsx';
import { useRef, useState } from 'react';
import { FaArrowDown, FaArrowUp } from 'react-icons/fa';

export function LandingContextSelection() {
  const users = [
    {
      userId: 1,
      name: 'Alice',
    },
    {
      userId: 2,
      name: 'Bob',
    },
    {
      userId: 3,
      name: 'Charlie',
    },
  ];
  const userMapping = { userId: 'ID', name: null };
  const outlets = [
    {
      outletId: 201,
      name: 'Indirapuram Dark Store',
      address: 'Indirapuram, Ghaziabad',
    },
    {
      outletId: 202,
      name: 'Vaishali Dark Store',
      address: 'Vaishali, Ghaziabad',
    },
  ];
  users.sort((user1, user2) => user1.userId - user2.userId);
  outlets.sort((outlet1, outlet2) => outlet1.outletId - outlet2.outletId);
  const outletMapping = { outletId: 'ID', name: null, address: null };
  const [selectedUserOutlet, setSelectedUserOutlet] = useState({
    userId: users[0]?.userId,
    outletId: outlets[0]?.outletId,
  });
  const [isUserCollapsed, setuserIsCollapsed] = useState(true);
  const [isOutletCollapsed, setOutletIsCollapsed] = useState(false);
  const groupName = useRef(Math.random().toString());

  const contextSelectionHandler = (selectedRow) => {
    if (Object.keys(selectedRow).includes('outletId')) {
      setSelectedUserOutlet({ ...selectedUserOutlet, outletId: selectedRow['outletId'] });
    }
    if (Object.keys(selectedRow).includes('userId')) {
      setSelectedUserOutlet({ ...selectedUserOutlet, userId: selectedRow['userId'] });
    }
  };
  return (
    <>
      <img src={JoiBranding} alt="JoiBranding" />
      <img src={LandingAbstractArt} alt="LandingAbstractArt" />
      <h2>Select your Context</h2>
      <h4>Set your shopping context to load available stock and local prices.</h4>
      <h3>Select User</h3>
      <SelectionCard
        data={[users.find((user) => user.userId === selectedUserOutlet.userId)]}
        mapping={userMapping}
        dynamicComponent={() => {
          return isUserCollapsed ? (
            <FaArrowUp onClick={() => setuserIsCollapsed(!isUserCollapsed)} />
          ) : (
            <FaArrowDown onClick={() => setuserIsCollapsed(!isUserCollapsed)} />
          );
        }}
      ></SelectionCard>
      {!isOutletCollapsed ? (
        <SelectionCard
          data={users}
          mapping={userMapping}
          dynamicComponent={(item) => (
            <input type="radio" onChange={() => contextSelectionHandler(item)} />
          )}
        ></SelectionCard>
      ) : null}
      <h3>Select Delivery Outlet</h3>
      <SelectionCard
        data={[outlets.find((outlet) => outlet.outletId === selectedUserOutlet.outletId)]}
        mapping={outletMapping}
        dynamicComponent={() => {
          return isUserCollapsed ? (
            <FaArrowUp onClick={() => setOutletIsCollapsed(!isOutletCollapsed)} />
          ) : (
            <FaArrowDown onClick={() => setOutletIsCollapsed(!isOutletCollapsed)} />
          );
        }}
      ></SelectionCard>
      {isOutletCollapsed ? (
        <SelectionCard
          data={outlets}
          mapping={outletMapping}
          dynamicComponent={(item) => (
            <input type="radio" onChange={() => contextSelectionHandler(item)} />
          )}
        ></SelectionCard>
      ) : null}
      <button onClick={(e) => console.log('Submitted', e.timeStamp)}>Start Shopping</button>
    </>
  );
}
