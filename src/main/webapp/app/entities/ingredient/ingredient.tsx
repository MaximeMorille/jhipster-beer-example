import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './ingredient.reducer';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIngredientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Ingredient = (props: IIngredientProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { ingredientList, match, loading } = props;
  return (
    <div>
      <h2 id="ingredient-heading" data-cy="IngredientHeading">
        <Translate contentKey="jhipsterBeerExampleApp.ingredient.home.title">Ingredients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterBeerExampleApp.ingredient.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterBeerExampleApp.ingredient.home.createLabel">Create new Ingredient</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ingredientList && ingredientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.uuid">Uuid</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.countryName">Country Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.beer">Beer</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ingredientList.map((ingredient, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${ingredient.id}`} color="link" size="sm">
                      {ingredient.id}
                    </Button>
                  </td>
                  <td>{ingredient.uuid}</td>
                  <td>{ingredient.name}</td>
                  <td>{ingredient.quantity}</td>
                  <td>{ingredient.unit}</td>
                  <td>{ingredient.countryName}</td>
                  <td>{ingredient.beer ? <Link to={`beer/${ingredient.beer.id}`}>{ingredient.beer.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ingredient.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ingredient.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ingredient.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.home.notFound">No Ingredients found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ ingredient }: IRootState) => ({
  ingredientList: ingredient.entities,
  loading: ingredient.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Ingredient);
