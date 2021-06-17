import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/company">
      Company
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-comment">
      User Comment
    </MenuItem>
    <MenuItem icon="asterisk" to="/tag">
      Tag
    </MenuItem>
    <MenuItem icon="asterisk" to="/staff">
      Staff
    </MenuItem>
    <MenuItem icon="asterisk" to="/file">
      File
    </MenuItem>
    <MenuItem icon="asterisk" to="/note">
      Note
    </MenuItem>
    <MenuItem icon="asterisk" to="/offer">
      Offer
    </MenuItem>
    <MenuItem icon="asterisk" to="/contract">
      Contract
    </MenuItem>
    <MenuItem icon="asterisk" to="/contract-kind">
      Contract Kind
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      Product
    </MenuItem>
    <MenuItem icon="asterisk" to="/facture">
      Facture
    </MenuItem>
    <MenuItem icon="asterisk" to="/facture-item">
      Facture Item
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
