import React from 'react';
import { instantMeiliSearch } from '@meilisearch/instant-meilisearch';
import { InfiniteHits, InstantSearch, SearchBox } from 'react-instantsearch';
import 'instantsearch.css/themes/algolia.css';
import { Link } from 'react-router-dom';

const { searchClient } = instantMeiliSearch(
  'http://localhost:7700'
);

const MeiliApp = () => (
  <InstantSearch
    indexName="SUrl"
    searchClient={searchClient}
  >
    <SearchBox />
    <InfiniteHits hitComponent={Hit}/>
  </InstantSearch>
);

const Hit = ({ hit }) => (
  <article className='card' key={hit.id}>
    {/* <div className='card'> */}
      <div className='card-body'>
        <div className='card-title'>
          <Link className='fs-4' to={`/modify/${hit.id}`}>Title : {hit.title ? hit.title : "No Title"}</Link>
        </div>
        <div className='card-text'>
          <p>Origin : {hit.origin}</p>
          <p>ShortUrl : {hit.shortUrl}</p>
        </div>


      </div>

    {/* </div> */}

    {/* <img src={hit.image} alt={hit.name}/> */}


  </article>
);

export default MeiliApp;