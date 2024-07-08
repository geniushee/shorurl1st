import React from 'react';
import { instantMeiliSearch } from '@meilisearch/instant-meilisearch';
import { InfiniteHits, InstantSearch, SearchBox } from 'react-instantsearch';
import 'instantsearch.css/themes/algolia.css';

const { searchClient } = instantMeiliSearch(
    'http://localhost:7700'
  );

const MeiliApp = () => (
    <InstantSearch
      indexName="SUrl"
      searchClient={searchClient}
    >
      <SearchBox />
      <InfiniteHits hitComponent={Hit} />
    </InstantSearch>
  );

const Hit = ({hit}) => (
    <article key={hit.id}>
        {/* <img src={hit.image} alt={hit.name}/> */}
        <h1>{hit.title}</h1>
        <p>{hit.origin}</p>
        <p>{hit.shortUrl}</p>
        <p>{hit.content}</p>
        {/* <p>{hit.description}</p> */}
    </article>

);

export default MeiliApp;