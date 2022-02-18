import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './citizens.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CitizensDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const citizensEntity = useAppSelector(state => state.citizens.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="citizensDetailsHeading">
          <Translate contentKey="hermesreact1App.citizens.detail.title">Citizens</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="hermesreact1App.citizens.title">Title</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.title}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="hermesreact1App.citizens.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.lastname}</dd>
          <dt>
            <span id="firstname">
              <Translate contentKey="hermesreact1App.citizens.firstname">Firstname</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.firstname}</dd>
          <dt>
            <span id="fathersName">
              <Translate contentKey="hermesreact1App.citizens.fathersName">Fathers Name</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.fathersName}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="hermesreact1App.citizens.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.comments}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="hermesreact1App.citizens.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>
            {citizensEntity.birthDate ? <TextFormat value={citizensEntity.birthDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="giortazi">
              <Translate contentKey="hermesreact1App.citizens.giortazi">Giortazi</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.giortazi}</dd>
          <dt>
            <span id="male">
              <Translate contentKey="hermesreact1App.citizens.male">Male</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.male ? 'true' : 'false'}</dd>
          <dt>
            <span id="meLetter">
              <Translate contentKey="hermesreact1App.citizens.meLetter">Me Letter</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.meLetter}</dd>
          <dt>
            <span id="meLabel">
              <Translate contentKey="hermesreact1App.citizens.meLabel">Me Label</Translate>
            </span>
          </dt>
          <dd>{citizensEntity.meLabel}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="hermesreact1App.citizens.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {citizensEntity.image ? (
              <div>
                {citizensEntity.imageContentType ? (
                  <a onClick={openFile(citizensEntity.imageContentType, citizensEntity.image)}>
                    <img src={`data:${citizensEntity.imageContentType};base64,${citizensEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {citizensEntity.imageContentType}, {byteSize(citizensEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.folder">Folder</Translate>
          </dt>
          <dd>{citizensEntity.folder ? citizensEntity.folder.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.company">Company</Translate>
          </dt>
          <dd>{citizensEntity.company ? citizensEntity.company.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.maritalStatus">Marital Status</Translate>
          </dt>
          <dd>{citizensEntity.maritalStatus ? citizensEntity.maritalStatus.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.team">Team</Translate>
          </dt>
          <dd>{citizensEntity.team ? citizensEntity.team.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.code">Code</Translate>
          </dt>
          <dd>{citizensEntity.code ? citizensEntity.code.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.origin">Origin</Translate>
          </dt>
          <dd>{citizensEntity.origin ? citizensEntity.origin.name : ''}</dd>
          <dt>
            <Translate contentKey="hermesreact1App.citizens.job">Job</Translate>
          </dt>
          <dd>{citizensEntity.job ? citizensEntity.job.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/citizens" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/citizens/${citizensEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CitizensDetail;
