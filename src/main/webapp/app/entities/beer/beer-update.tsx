import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './beer.reducer';
import { IBeer } from 'app/shared/model/beer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBeerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BeerUpdate = (props: IBeerUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { beerEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/beer' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...beerEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterBeerExampleApp.beer.home.createOrEditLabel" data-cy="BeerCreateUpdateHeading">
            <Translate contentKey="jhipsterBeerExampleApp.beer.home.createOrEditLabel">Create or edit a Beer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : beerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="beer-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="beer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="uuidLabel" for="beer-uuid">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.uuid">Uuid</Translate>
                </Label>
                <AvField id="beer-uuid" data-cy="uuid" type="text" name="uuid" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="beer-name">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.name">Name</Translate>
                </Label>
                <AvField id="beer-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="abvLabel" for="beer-abv">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.abv">Abv</Translate>
                </Label>
                <AvField id="beer-abv" data-cy="abv" type="string" className="form-control" name="abv" />
              </AvGroup>
              <AvGroup>
                <Label id="ebcLabel" for="beer-ebc">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.ebc">Ebc</Translate>
                </Label>
                <AvField id="beer-ebc" data-cy="ebc" type="string" className="form-control" name="ebc" />
              </AvGroup>
              <AvGroup>
                <Label id="ibuLabel" for="beer-ibu">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.ibu">Ibu</Translate>
                </Label>
                <AvField id="beer-ibu" data-cy="ibu" type="string" className="form-control" name="ibu" />
              </AvGroup>
              <AvGroup>
                <Label id="ownerUuidLabel" for="beer-ownerUuid">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.ownerUuid">Owner Uuid</Translate>
                </Label>
                <AvField id="beer-ownerUuid" data-cy="ownerUuid" type="text" name="ownerUuid" />
              </AvGroup>
              <AvGroup>
                <Label id="colorLabel" for="beer-color">
                  <Translate contentKey="jhipsterBeerExampleApp.beer.color">Color</Translate>
                </Label>
                <AvInput
                  id="beer-color"
                  data-cy="color"
                  type="select"
                  className="form-control"
                  name="color"
                  value={(!isNew && beerEntity.color) || 'BLONDE'}
                >
                  <option value="BLONDE">{translate('jhipsterBeerExampleApp.BeerColor.BLONDE')}</option>
                  <option value="BRUNE">{translate('jhipsterBeerExampleApp.BeerColor.BRUNE')}</option>
                  <option value="AMBREE">{translate('jhipsterBeerExampleApp.BeerColor.AMBREE')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/beer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  beerEntity: storeState.beer.entity,
  loading: storeState.beer.loading,
  updating: storeState.beer.updating,
  updateSuccess: storeState.beer.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BeerUpdate);
