import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './beer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBeerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BeerDetail = (props: IBeerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { beerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beerDetailsHeading">
          <Translate contentKey="jhipsterBeerExampleApp.beer.detail.title">Beer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{beerEntity.id}</dd>
          <dt>
            <span id="uuid">
              <Translate contentKey="jhipsterBeerExampleApp.beer.uuid">Uuid</Translate>
            </span>
          </dt>
          <dd>{beerEntity.uuid}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterBeerExampleApp.beer.name">Name</Translate>
            </span>
          </dt>
          <dd>{beerEntity.name}</dd>
          <dt>
            <span id="abv">
              <Translate contentKey="jhipsterBeerExampleApp.beer.abv">Abv</Translate>
            </span>
          </dt>
          <dd>{beerEntity.abv}</dd>
          <dt>
            <span id="ebc">
              <Translate contentKey="jhipsterBeerExampleApp.beer.ebc">Ebc</Translate>
            </span>
          </dt>
          <dd>{beerEntity.ebc}</dd>
          <dt>
            <span id="ibu">
              <Translate contentKey="jhipsterBeerExampleApp.beer.ibu">Ibu</Translate>
            </span>
          </dt>
          <dd>{beerEntity.ibu}</dd>
          <dt>
            <span id="ownerUuid">
              <Translate contentKey="jhipsterBeerExampleApp.beer.ownerUuid">Owner Uuid</Translate>
            </span>
          </dt>
          <dd>{beerEntity.ownerUuid}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="jhipsterBeerExampleApp.beer.color">Color</Translate>
            </span>
          </dt>
          <dd>{beerEntity.color}</dd>
        </dl>
        <Button tag={Link} to="/beer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beer/${beerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ beer }: IRootState) => ({
  beerEntity: beer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BeerDetail);
